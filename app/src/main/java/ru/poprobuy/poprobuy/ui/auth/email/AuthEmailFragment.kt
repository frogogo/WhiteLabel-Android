package ru.poprobuy.poprobuy.ui.auth.email

import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentAuthEmailBinding
import ru.poprobuy.poprobuy.extension.hideKeyboard
import ru.poprobuy.poprobuy.extension.initEmailType
import ru.poprobuy.poprobuy.extension.setNullableTextRes
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener

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
        showKeyboard()
        setEditGoAction { setEmail() }
        initEmailType()
      }
      buttonContinue.setOnSafeClickListener { setEmail() }
    }
  }

  override fun initObservers() = viewModel.run {
    emailValidationLiveEvent.observe { errorRes ->
      binding.textViewError.setNullableTextRes(errorRes)
      binding.textInputLayout.setError(errorRes != null)
    }
    isLoadingLive.observe { isLoading ->
      binding.textInputLayout.setLoading(isLoading)
      binding.buttonContinue.isEnabled = !isLoading
    }
    command.observe { command ->
      when (command) {
        AuthEmailCommand.HideKeyboard -> requireContext().hideKeyboard()
      }
    }
  }

  private fun setEmail() {
    viewModel.updateUserData(binding.textInputLayout.text)
  }

}
