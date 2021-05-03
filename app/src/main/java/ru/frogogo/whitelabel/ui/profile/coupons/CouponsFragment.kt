package ru.frogogo.whitelabel.ui.profile.coupons

import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen

class CouponsFragment : BaseFragment<CouponsViewModel>(),
  AndroidScopeComponent {

  override val scope: Scope by fragmentScope()
  override val viewModel: CouponsViewModel by viewModel()

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_coupons,
    screen = AnalyticsScreen.COUPONS,
  )
}
