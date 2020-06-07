package ru.poprobuy.poprobuy.ui.onboarding

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface OnboardingNavigation {
  fun navigateToLogin(): NavigationCommand
}

class OnboardingNavigationImpl : OnboardingNavigation {

  override fun navigateToLogin(): NavigationCommand {
    return NavigationCommand.Back
  }

}
