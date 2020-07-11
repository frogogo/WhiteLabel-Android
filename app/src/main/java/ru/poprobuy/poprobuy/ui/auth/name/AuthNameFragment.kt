package ru.poprobuy.poprobuy.ui.auth.name

import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentAuthNameBinding
import ru.poprobuy.poprobuy.extension.initUserNameType
import ru.poprobuy.poprobuy.extension.setNullableTextRes
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener

class AuthNameFragment : BaseFragment<AuthNameViewModel>(
  layoutId = R.layout.fragment_auth_name,
  windowAnimations = true
) {

  override val viewModel: AuthNameViewModel by viewModel()

  private val binding: FragmentAuthNameBinding by viewBinding()

  override fun initViews() {
    // Force keyboard
    binding.textInputLayout.apply {
      showKeyboard()
      setEditGoAction { setName() }
      initUserNameType()
    }

    binding.buttonContinue.setOnSafeClickListener { setName() }
  }

  override fun initObservers() = viewModel.run {
    nameValidationLiveEvent.observe { errorRes ->
      binding.textViewError.setNullableTextRes(errorRes)
      binding.textInputLayout.setError(errorRes != null)
    }
  }

  private fun setName() {
    viewModel.setUserName(binding.textInputLayout.text)
  }

}
