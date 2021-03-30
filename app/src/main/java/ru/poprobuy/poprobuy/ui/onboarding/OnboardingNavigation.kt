package ru.poprobuy.poprobuy.ui.onboarding

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.extension.toCommand

interface OnboardingNavigation {
  fun navigateToAuth(): NavigationCommand
}

class OnboardingNavigationImpl : OnboardingNavigation {

  override fun navigateToAuth(): NavigationCommand =
    OnboardingFragmentDirections.onboardingToAuthPolicy().toCommand()
}
