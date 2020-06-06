package ru.poprobuy.poprobuy.ui.splash

import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.ui.base.BaseFragment

class SplashFragment : BaseFragment<SplashViewModel>(R.layout.fragment_splash) {

  override val viewModel: SplashViewModel by viewModel()

}
