package ru.poprobuy.poprobuy.ui.auth.code

import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentAuthCodeConfirmationBinding
import ru.poprobuy.poprobuy.extension.*
import ru.poprobuy.poprobuy.util.Constants

class AuthCodeConfirmationFragment : BaseFragment<AuthCodeConfirmationViewModel>(
  layoutId = R.layout.fragment_auth_code_confirmation,
  windowAnimations = true
) {

  override val viewModel: AuthCodeConfirmationViewModel by viewModel()

  private val binding: FragmentAuthCodeConfirmationBinding by viewBinding()
  private val args: AuthCodeConfirmationFragmentArgs by navArgs()
  private var codeTextWatcher: TextWatcher? = null

  override fun initViews() {
    binding.apply {
      textInputLayout.apply {
        showKeyboard()
        setEditGoAction { confirmPhone() }
        initCodeConfirmationType()
      }

      textViewPhoneNumber.text = args.phoneNumber.formatWithMask(Constants.PHONE_MASK_FULL)
      textViewPhoneNumberChange.setOnSafeClickListener { viewModel.navigateBack() }
      buttonResendCode.setOnSafeClickListener { viewModel.resendConfirmationCode() }
    }
  }

  override fun onResume() {
    super.onResume()
    codeTextWatcher = binding.textInputLayout.binding.editText.addTextChangedListener {
      if (it.toString().length == Constants.CONFIRMATION_CODE_LENGTH) {
        confirmPhone()
      }
    }
  }

  override fun onPause() {
    super.onPause()
    // Unregister text changed listener to prevent calls when returning back from another fragment
    binding.textInputLayout.binding.editText.removeTextChangedListener(codeTextWatcher)
  }

  override fun initObservers() = viewModel.run {
    codeValidationLiveEvent.observe {
      binding.textViewError.setNullableTextRes(it)
      binding.textInputLayout.setError(it != null)
    }
    isLoadingLive.observe { binding.textInputLayout.setLoading(it) }
    resendCodeButtonState.observe { renderResendCodeButton(it) }
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

}
