package ru.poprobuy.poprobuy.ui.splash

import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.i
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.usecase.GetUserAuthStateUseCase
import ru.poprobuy.poprobuy.usecase.GetUserAuthStateUseCase.State

class SplashViewModel(
  private val getUserAuthStateUseCase: GetUserAuthStateUseCase,
  private val navigation: SplashNavigation
) : BaseViewModel() {

  override fun onCreate() {
    navigateNext()
  }

  private fun navigateNext() = viewModelScope.launch {
    delay(750)
    when (getUserAuthStateUseCase()) {
      State.CLEAN_START -> {
        i { "Navigating to onboarding" }
        navigation.navigateToOnboarding()
      }
      State.ONBOARDING_COMPLETED -> {
        i { "Navigating to policy" }
        navigation.navigateToPolicy()
      }
      State.POLICY_ACCEPTED -> {
        i { "Navigating to auth" }
        navigation.navigateToAuth()
      }
      State.LOGGED_IN -> {
        i { "Navigating to app" }
        navigation.navigateToApp()
      }
    }.navigate()
  }

}
