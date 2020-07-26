package ru.poprobuy.poprobuy.ui.auth.email

import android.text.method.LinkMovementMethod
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentAuthEmailBinding
import ru.poprobuy.poprobuy.extension.binding.initEmailType
import ru.poprobuy.poprobuy.extension.setNullableTextRes
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.util.SpannableUtils

class AuthEmailFragment : BaseFragment<AuthEmailViewModel>(
  layoutId = R.layout.fragment_auth_email,
  windowAnimations = true
) {

  override val viewModel: AuthEmailViewModel by viewModel { parametersOf(args.name) }

  private val binding: FragmentAuthEmailBinding by viewBinding()
  private val args: AuthEmailFragmentArgs by navArgs()

  override fun initViews() {
    binding.apply {
      textViewTitle.text = getString(R.string.auth_email_title, args.name)
      textInputLayout.apply {
        initEmailType()
        setEditGoAction { setEmail() }
        showKeyboard()
      }
      buttonContinue.setOnSafeClickListener { setEmail() }
      textViewError.movementMethod = LinkMovementMethod.getInstance()
    }
  }

  override fun initObservers() = viewModel.run {
    isLoadingLive.observe { isLoading ->
      binding.textInputLayout.setLoading(isLoading)
      binding.buttonContinue.isEnabled = !isLoading
    }
    commandLiveEvent.observe(this@AuthEmailFragment::handleCommand)
  }

  private fun handleCommand(command: AuthEmailCommand) {
    when (command) {
      AuthEmailCommand.SomethingWentWrong -> binding.apply {
        textInputLayout.setError(true)
        textViewError.text = SpannableUtils.createSomethingWentWrongSpan(requireContext()) {
          setEmail()
        }
      }
      is AuthEmailCommand.EmailValidationResult -> binding.apply {
        textViewError.setNullableTextRes(command.errorRes)
        textInputLayout.setError(command.errorRes != null)
      }
    }
  }

  private fun setEmail() {
    viewModel.updateUserData(binding.textInputLayout.text)
  }

}
