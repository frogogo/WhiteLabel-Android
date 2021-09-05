package ru.frogogo.whitelabel.ui.splash

import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen

class SplashFragment : BaseFragment<SplashViewModel>() {

  override val viewModel: SplashViewModel by viewModel()

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_splash,
    screen = AnalyticsScreen.SPLASH,
  )
}
