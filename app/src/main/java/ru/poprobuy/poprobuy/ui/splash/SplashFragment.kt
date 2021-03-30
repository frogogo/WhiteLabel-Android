package ru.poprobuy.poprobuy.ui.splash

import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.ui.BaseFragment
import ru.poprobuy.poprobuy.util.analytics.AnalyticsScreen

class SplashFragment : BaseFragment<SplashViewModel>(
  layoutId = R.layout.fragment_splash,
  screen = AnalyticsScreen.SPLASH
) {

  override val viewModel: SplashViewModel by viewModel()
}
