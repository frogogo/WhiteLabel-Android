package ru.poprobuy.poprobuy.ui.auth.code

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
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

class AuthCodeFragment : BaseFragment<AuthCodeViewModel>(
  layoutId = R.layout.fragment_auth_code,
  windowAnimations = true
) {

  override val viewModel: AuthCodeViewModel by viewModel { parametersOf(args.phoneNumber) }

  private val binding: FragmentAuthCodeBinding by viewBinding()
  private val args: AuthCodeFragmentArgs by navArgs()
  private val smsVerificationReceiver: SmsVerificationReceiver by lazy {
    SmsVerificationReceiver(SMS_CONSENT_REQUEST) { this }
  }
  private var codeTextWatcher: TextWatcher? = null

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
    }
  }

  override fun initObservers() = viewModel.run {
    commandLiveEvent.observe(this@AuthCodeFragment::handleCommand)
    isLoadingLive.observe(this@AuthCodeFragment.binding.textInputLayout::setLoading)
    resendCodeButtonState.observe(this@AuthCodeFragment::renderResendCodeButton)
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

  companion object {
    private const val SMS_CONSENT_REQUEST = 2
  }

}
