package ru.poprobuy.poprobuy.ui.splash

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface SplashNavigation {
  fun navigateToLogin(): NavigationCommand
  fun navigateToApp(): NavigationCommand
  fun navigateToOnboarding(): NavigationCommand
}

class SplashNavigationImpl : SplashNavigation {

  override fun navigateToLogin(): NavigationCommand {
    return NavigationCommand.Back // TODO: 06.06.2020
  }

  override fun navigateToApp(): NavigationCommand {
    return NavigationCommand.Back // TODO: 06.06.2020
  }

  override fun navigateToOnboarding(): NavigationCommand {
    return NavigationCommand.ById(R.id.splash_to_onboarding)
  }

}
