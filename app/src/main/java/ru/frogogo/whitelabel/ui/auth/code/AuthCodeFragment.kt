package ru.frogogo.whitelabel.ui.auth.code

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
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.databinding.FragmentAuthCodeBinding
import ru.frogogo.whitelabel.extension.*
import ru.frogogo.whitelabel.extension.binding.editText
import ru.frogogo.whitelabel.extension.binding.initCodeConfirmationType
import ru.frogogo.whitelabel.util.*
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen

class AuthCodeFragment : BaseFragment<AuthCodeViewModel>() {

  override val viewModel: AuthCodeViewModel by viewModel { parametersOf(args.phoneNumber) }

  private val binding: FragmentAuthCodeBinding by viewBinding()
  private val args: AuthCodeFragmentArgs by navArgs()
  private val smsVerificationReceiver: SmsVerificationReceiver by unsafeLazy {
    SmsVerificationReceiver(SMS_CONSENT_ACTIVITY_REQUEST_CODE) { this }
  }
  private var codeTextWatcher: TextWatcher? = null
  private val handler = Handler()

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_auth_code,
    screen = AnalyticsScreen.AUTH_CODE,
    windowAnimations = true
  )

  override fun initViews() {
    binding.apply {
      textInputLayout.apply {
        initCodeConfirmationType()
        setEditGoAction { confirmPhone() }
        showKeyboard()
      }

      textViewPhoneNumber.text = args.phoneNumber.formatWithMask(Constants.PHONE_MASK_FULL)
      textViewPhoneNumberChange.setSafeOnClickListener(viewModel::navigateBack)
      buttonResendCode.setSafeOnClickListener(viewModel::resendConfirmationCode)
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

  override fun hideKeyboard() {
    super.hideKeyboard()
    binding.textInputLayout.hideKeyboard()
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel.setResendDelay(args.codeRefreshRate)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    SmsRetriever.getClient(requireContext())
      .startSmsUserConsent(null) // TODO: 01.12.2020 Return handling sms from specific sender
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

    if (requestCode == SMS_CONSENT_ACTIVITY_REQUEST_CODE) {
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
    private const val SMS_CONSENT_ACTIVITY_REQUEST_CODE = 2
  }
}
