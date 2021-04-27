package ru.frogogo.whitelabel.ui.splash

import ru.frogogo.whitelabel.core.navigation.NavigationCommand

interface SplashNavigation {
  fun navigateToPolicy(): NavigationCommand
  fun navigateToAuth(): NavigationCommand
  fun navigateToApp(): NavigationCommand
  fun navigateToOnboarding(): NavigationCommand
}

class SplashNavigationImpl : SplashNavigation {

  override fun navigateToPolicy(): NavigationCommand =
    SplashFragmentDirections.splashToAuthPolicy().toCommand()

  override fun navigateToAuth(): NavigationCommand =
    SplashFragmentDirections.splashToAuth().toCommand()

  override fun navigateToApp(): NavigationCommand =
    SplashFragmentDirections.splashToHome().toCommand()

  override fun navigateToOnboarding(): NavigationCommand =
    SplashFragmentDirections.splashToOnboarding().toCommand()
}
