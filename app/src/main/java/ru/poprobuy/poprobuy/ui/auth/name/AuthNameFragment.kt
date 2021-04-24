package ru.poprobuy.poprobuy.ui.auth.name

import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentAuthNameBinding
import ru.poprobuy.poprobuy.extension.binding.initUserNameType
import ru.poprobuy.poprobuy.extension.observe
import ru.poprobuy.poprobuy.extension.setNullableTextRes
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.util.analytics.AnalyticsScreen

class AuthNameFragment : BaseFragment<AuthNameViewModel>() {

  override val viewModel: AuthNameViewModel by viewModel()

  private val binding: FragmentAuthNameBinding by viewBinding()

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_auth_name,
    screen = AnalyticsScreen.AUTH_NAME,
    windowAnimations = true
  )

  override fun initViews() {
    // Force keyboard
    binding.textInputLayout.apply {
      initUserNameType()
      setEditGoAction { setName() }
      showKeyboard()
    }

    binding.buttonContinue.setOnSafeClickListener { setName() }
  }

  override fun initObservers() {
    observe(viewModel.nameValidationLiveEvent) { errorRes ->
      binding.textViewError.setNullableTextRes(errorRes)
      binding.textInputLayout.setError(errorRes != null)
    }
  }

  private fun setName() {
    viewModel.setUserName(binding.textInputLayout.text)
  }
}
