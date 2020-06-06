package ru.poprobuy.poprobuy.ui.splash

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.ui.base.BaseViewModel
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
    val command = when (getUserAuthStateUseCase()) {
      State.LOGGED_IN -> navigation.navigateToApp()
      State.ONBOARDING_VIEWED -> navigation.navigateToLogin()
      State.CLEAN_START -> navigation.navigateToOnboarding()
    }

    navigate(command)
  }

}
