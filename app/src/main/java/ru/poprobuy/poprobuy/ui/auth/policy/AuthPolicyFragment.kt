package ru.poprobuy.poprobuy.ui.auth.policy

import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentAuthPolicyBinding
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.util.analytics.AnalyticsScreen

class AuthPolicyFragment : BaseFragment<AuthPolicyViewModel>() {

  override val viewModel: AuthPolicyViewModel by viewModel()

  private val binding: FragmentAuthPolicyBinding by viewBinding()

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_auth_policy,
    screen = AnalyticsScreen.AUTH_POLICY
  )

  override fun initViews() {
    binding.buttonContinue.setOnSafeClickListener(viewModel::navigateNext)
  }
}
