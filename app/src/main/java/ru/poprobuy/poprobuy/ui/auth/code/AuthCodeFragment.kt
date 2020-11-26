package ru.poprobuy.poprobuy.ui.auth.code

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import androidx.core.os.postDelayed
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import com.github.razir.progressbutton.*
import com.google.android.gms.auth.api.phone.SmsRetriever
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentAuthCodeBinding
import ru.poprobuy.poprobuy.extension.*
import ru.poprobuy.poprobuy.extension.binding.editText
import ru.poprobuy.poprobuy.extension.binding.initCodeConfirmationType
import ru.poprobuy.poprobuy.util.ConfirmationCodeUtils
import ru.poprobuy.poprobuy.util.Constants
import ru.poprobuy.poprobuy.util.ParallelAutoTransition
import ru.poprobuy.poprobuy.util.SpannableUtils
import ru.poprobuy.poprobuy.util.analytics.AnalyticsScreen

class AuthCodeFragment : BaseFragment<AuthCodeViewModel>(
  layoutId = R.layout.fragment_auth_code,
  screen = AnalyticsScreen.AUTH_CODE,
  windowAnimations = true
) {

  override val viewModel: AuthCodeViewModel by viewModel { parametersOf(args.phoneNumber) }

  private val binding: FragmentAuthCodeBinding by viewBinding()
  private val args: AuthCodeFragmentArgs by navArgs()
  private val smsVerificationReceiver: SmsVerificationReceiver by lazy {
    SmsVerificationReceiver(SMS_CONSENT_REQUEST) { this }
  }
  private var codeTextWatcher: TextWatcher? = null
  private val handler = Handler()

  override fun initViews() {
    binding.apply {
      textInputLayout.apply {
        initCodeConfirmationType()
        setEditGoAction { confirmPhone() }
        showKeyboard()
      }

      textViewPhoneNumber.text = args.phoneNumber.formatWithMask(Constants.PHONE_MASK_FULL)
      textViewPhoneNumberChange.setOnSafeClickListener(viewModel::navigateBack)
      buttonResendCode.setOnSafeClickListener(viewModel::resendConfirmationCode)
      textViewError.movementMethod = LinkMovementMethod.getInstance()
      bindProgressButton(buttonResendCode)
    }
  }

  override fun initObservers() {
    with(viewModel) {
      observe(commandLiveEvent, ::handleCommand)
      observe(isLoadingLive, binding.textInputLayout::setLoading)
      observe(isResendingCodeLive, ::renderCodeIsResending)
      observe(resendCodeTimeRemainingLive, ::renderResendCodeButton)
    }
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel.setResendDelay(args.codeRefreshRate)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    SmsRetriever.getClient(requireContext())
      .startSmsUserConsent(Constants.SMS_SENDER_NAME)
  }

  override fun onStart() {
    super.onStart()

    val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
    requireActivity().registerReceiver(smsVerificationReceiver, intentFilter)
  }

  override fun onStop() {
    super.onStop()
    handler.removeCallbacksAndMessages(null)
    requireActivity().unregisterReceiver(smsVerificationReceiver)
  }

  override fun onResume() {
    super.onResume()
    codeTextWatcher = binding.textInputLayout.editText.addTextChangedListener { editable ->
      if (editable.toString().length == Constants.CONFIRMATION_CODE_LENGTH) {
        confirmPhone()
      }
    }
  }

  override fun onPause() {
    super.onPause()
    // Unregister text changed listener to prevent calls when returning back from another fragment
    binding.textInputLayout.binding.editText.removeTextChangedListener(codeTextWatcher)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == SMS_CONSENT_REQUEST) {
      if (resultCode == Activity.RESULT_OK && data != null) {
        val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
        i { "Received message - $message" }
        val confirmationCode = ConfirmationCodeUtils.extractConfirmationCodeFromSms(message)
        i { "Extracted code - $confirmationCode" }
        confirmationCode?.let {
          i { "Setting code to text input layout" }
          binding.textInputLayout.text = it
          confirmPhone()
        }
      } else {
        e { "Consent denied" }
      }
    }
  }

  private fun handleCommand(command: AuthCodeCommand) {
    TransitionManager.beginDelayedTransition(binding.layoutContent, ParallelAutoTransition().apply {
      excludeChildren(binding.textInputLayout, true)
    })
    @Exhaustive
    when (command) {
      AuthCodeCommand.ClearError -> binding.apply {
        textInputLayout.setError(false)
        textViewError.text = null
      }
      AuthCodeCommand.UserNotFoundError -> binding.apply {
        textInputLayout.setError(true)
        textViewError.setText(R.string.auth_code_error_invalid_code)
      }
      AuthCodeCommand.SomethingWentWrong -> binding.apply {
        textInputLayout.setError(true)
        textViewError.text = SpannableUtils.createSomethingWentWrongSpan(requireContext()) {
          confirmPhone()
        }
      }
      AuthCodeCommand.CodeResendError -> {
        val drawableSize = resources.getDimensionPixelSize(R.dimen.button_icon_size)
        val drawable = requireContext().fetchDrawable(R.drawable.ic_exclamation_mark_primary)!!.apply {
          setBounds(0, 0, drawableSize, drawableSize)
        }
        binding.buttonResendCode.showDrawable(drawable) {
          buttonTextRes = R.string.auth_code_error_resend
        }
        handler.postDelayed(CODE_RESEND_ERROR_DURATION) {
          binding.buttonResendCode.hideDrawable(R.string.auth_code_button_resend_code)
        }
      }
      is AuthCodeCommand.CodeValidationResult -> binding.apply {
        textViewError.setNullableTextRes(command.errorRes)
        textInputLayout.setError(command.errorRes != null)
      }
    }
  }

  private fun confirmPhone() {
    viewModel.confirmPhoneNumber(binding.textInputLayout.text)
  }

  private fun renderResendCodeButton(secondsRemaining: Int) {
    val enabled = secondsRemaining <= 0
    val buttonTextAwait = resources.getQuantityString(R.plurals.seconds_remaining, secondsRemaining, secondsRemaining)

    binding.apply {
      buttonResendCodeAwait.apply {
        setVisible(!enabled)
        text = buttonTextAwait
      }
      buttonResendCode.setVisible(enabled)
    }
  }

  private fun renderCodeIsResending(isResending: Boolean) {
    if (isResending) {
      binding.buttonResendCode.showProgress {
        buttonTextRes = R.string.auth_code_button_resend_resending
        progressColorRes = R.color.button
      }
    } else {
      binding.buttonResendCode.hideProgress(R.string.auth_code_button_resend_code)
    }
  }

  companion object {
    private const val CODE_RESEND_ERROR_DURATION = 1500L
    private const val SMS_CONSENT_REQUEST = 2
  }

}
