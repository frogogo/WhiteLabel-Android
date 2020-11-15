package ru.poprobuy.poprobuy.ui.onboarding

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface OnboardingNavigation {
  fun navigateToAuth(): NavigationCommand
}

class OnboardingNavigationImpl : OnboardingNavigation {

  override fun navigateToAuth(): NavigationCommand {
    val action = OnboardingFragmentDirections.onboardingToAuthPolicy()
    return NavigationCommand.ByAction(action)
  }

}
