package ru.poprobuy.poprobuy.ui.splash

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand

interface SplashNavigation {
  fun navigateToPolicy(): NavigationCommand
  fun navigateToAuth(): NavigationCommand
  fun navigateToApp(): NavigationCommand
  fun navigateToOnboarding(): NavigationCommand
}

class SplashNavigationImpl : SplashNavigation {

  override fun navigateToPolicy(): NavigationCommand {
    val action = SplashFragmentDirections.splashToAuthPolicy()
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToAuth(): NavigationCommand {
    val action = SplashFragmentDirections.splashToAuth()
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToApp(): NavigationCommand {
    val action = SplashFragmentDirections.splashToHome()
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToOnboarding(): NavigationCommand {
    val action = SplashFragmentDirections.splashToOnboarding()
    return NavigationCommand.ByAction(action)
  }

}
