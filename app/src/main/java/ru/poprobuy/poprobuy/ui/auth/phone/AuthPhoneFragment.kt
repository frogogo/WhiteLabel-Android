package ru.poprobuy.poprobuy.ui.auth.phone

import android.text.method.LinkMovementMethod
import androidx.transition.TransitionManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentAuthPhoneBinding
import ru.poprobuy.poprobuy.extension.editText
import ru.poprobuy.poprobuy.extension.initPhoneType
import ru.poprobuy.poprobuy.extension.setNullableTextRes
import ru.poprobuy.poprobuy.util.Constants
import ru.poprobuy.poprobuy.util.ParallelAutoTransition
import ru.poprobuy.poprobuy.util.SpannableUtils

class AuthPhoneFragment : BaseFragment<AuthPhoneViewModel>(
  layoutId = R.layout.fragment_auth_phone,
  windowAnimations = true
), MaskedTextChangedListener.ValueListener {

  override val viewModel: AuthPhoneViewModel by viewModel()

  private val binding: FragmentAuthPhoneBinding by viewBinding()
  private var textChangedListener: MaskedTextChangedListener? = null

  override fun initViews() {
    binding.textInputLayout.apply {
      initPhoneType()
      setEditGoAction { viewModel.requestCode(text, true) }
      showKeyboard()
    }
    binding.textViewError.movementMethod = LinkMovementMethod.getInstance()
  }

  override fun initObservers() = viewModel.run {
    commandLiveEvent.observe(this@AuthPhoneFragment::handleCommand)
    isLoadingLive.observe(binding.textInputLayout::setLoading)
  }

  override fun onStart() {
    super.onStart()
    attachFormatter()
  }

  override fun onStop() {
    super.onStop()
    detachFormatter()
  }

  override fun onTextChanged(maskFilled: Boolean, extractedValue: String, formattedValue: String) {
    viewModel.requestCode(extractedValue)
  }

  private fun handleCommand(command: AuthPhoneCommand) {
    TransitionManager.beginDelayedTransition(binding.layoutContent, ParallelAutoTransition().apply {
      excludeChildren(binding.textInputLayout, true)
    })
    when (command) {
      AuthPhoneCommand.ClearError -> binding.apply {
        textInputLayout.setError(false)
        textViewError.text = null
      }
      AuthPhoneCommand.SomethingWentWrong -> binding.apply {
        textInputLayout.setError(true)
        textViewError.text = SpannableUtils.createSomethingWentWrongSpan(requireContext()) {
          viewModel.requestCode(textInputLayout.text)
        }
      }
      AuthPhoneCommand.TooManyRequestsError -> binding.apply {
        textInputLayout.setError(true)
        textViewError.setText(R.string.auth_phone_error_too_many_requests)
      }
      is AuthPhoneCommand.PhoneValidationResult -> binding.apply {
        textViewError.setNullableTextRes(command.errorRes)
        textInputLayout.setError(command.errorRes != null)
      }
    }
  }

  private fun attachFormatter() {
    textChangedListener = MaskedTextChangedListener.installOn(
      editText = binding.textInputLayout.editText,
      primaryFormat = Constants.PHONE_MASK,
      valueListener = this,
      autocomplete = false // Prevent handling focus change
    )
  }

  private fun detachFormatter() {
    binding.textInputLayout.editText.removeTextChangedListener(textChangedListener)
  }

}
