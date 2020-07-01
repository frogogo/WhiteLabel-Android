package ru.poprobuy.poprobuy.ui.auth.email

import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.arch.ui.viewBinding
import ru.poprobuy.poprobuy.databinding.FragmentAuthEmailBinding
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
    emailValidationLiveEvent.observe {
      binding.textViewError.setNullableTextRes(it)
      binding.textInputLayout.setError(it != null)
    }
    isLoadingLive.observe {
      binding.textInputLayout.setLoading(it)
      binding.buttonContinue.isEnabled = !it
    }
  }

  private fun setEmail() {
    viewModel.updateUserData(binding.textInputLayout.text)
  }

}
