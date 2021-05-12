package ru.frogogo.whitelabel.ui.auth.name

import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.databinding.FragmentAuthNameBinding
import ru.frogogo.whitelabel.extension.binding.initUserNameType
import ru.frogogo.whitelabel.extension.observe
import ru.frogogo.whitelabel.extension.setNullableTextRes
import ru.frogogo.whitelabel.extension.setOnSafeClickListener
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen

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
