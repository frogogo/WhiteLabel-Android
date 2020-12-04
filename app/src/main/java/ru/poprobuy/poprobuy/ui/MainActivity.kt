package ru.poprobuy.poprobuy.ui

import androidx.navigation.NavController
import androidx.navigation.findNavController
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.navigation.NavigationRouter
import ru.poprobuy.poprobuy.core.ui.BaseActivity
import ru.poprobuy.poprobuy.data.network.interceptor.AutoLogoutNotifier
import ru.poprobuy.poprobuy.util.observeEvent

class MainActivity : BaseActivity<MainViewModel>(R.layout.activity_main) {

  override val viewModel: MainViewModel by viewModel()
  private val logoutNotifier: AutoLogoutNotifier by inject()

  private val navigationRouter: NavigationRouter by inject { parametersOf(navController) }
  private val navController: NavController by lazy { findNavController(R.id.mainNavHost) }

  override fun initObservers() {
    viewModel.navigationLiveEvent.observeEvent(this, navigationRouter::navigate)
    logoutNotifier.logoutEvent.observeEvent(this) {
      viewModel.logout()
    }
  }

}
