package ru.frogogo.whitelabel.ui.coupon_info

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.extension.unloadBindingModuleOnClose
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen

class CouponInfoFragment : BaseFragment<CouponInfoViewModel>(),
  AndroidScopeComponent {

  override val scope: Scope by fragmentScope()
  override val viewModel: CouponInfoViewModel by viewModel { parametersOf(args.coupon) }

  private val args: CouponInfoFragmentArgs by navArgs()

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_coupon_info,
    screen = AnalyticsScreen.COUPON_INFO,
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    scope.unloadBindingModuleOnClose()
  }
}
