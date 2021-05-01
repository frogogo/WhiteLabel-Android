package ru.frogogo.whitelabel.ui

import androidx.navigation.NavController
import androidx.navigation.findNavController
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.navigation.NavigationRouter
import ru.frogogo.whitelabel.core.observeEvent
import ru.frogogo.whitelabel.core.ui.BaseActivity
import ru.frogogo.whitelabel.data.network.interceptor.AutoLogoutNotifier
import ru.frogogo.whitelabel.util.unsafeLazy

class MainActivity : BaseActivity<MainViewModel>(R.layout.activity_main) {

  override val viewModel: MainViewModel by viewModel()
  private val logoutNotifier: AutoLogoutNotifier by inject()

  private val navigationRouter: NavigationRouter by inject { parametersOf(navController) }
  private val navController: NavController by unsafeLazy { findNavController(R.id.main_nav_host) }

  override fun initObservers() {
    viewModel.navigationLiveEvent.observeEvent(this, navigationRouter::navigate)
    logoutNotifier.logoutEvent.observeEvent(this) {
      viewModel.logout()
    }
  }
}
