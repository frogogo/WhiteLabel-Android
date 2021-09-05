package ru.frogogo.whitelabel.ui.auth.policy

import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.databinding.FragmentAuthPolicyBinding
import ru.frogogo.whitelabel.extension.setSafeOnClickListener
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen

class AuthPolicyFragment : BaseFragment<AuthPolicyViewModel>() {

  override val viewModel: AuthPolicyViewModel by viewModel()

  private val binding: FragmentAuthPolicyBinding by viewBinding()

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_auth_policy,
    screen = AnalyticsScreen.AUTH_POLICY,
  )

  override fun initViews() {
    binding.buttonContinue.setSafeOnClickListener(viewModel::navigateNext)
  }
}
