package ru.frogogo.whitelabel.ui.onboarding

import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.extension.toCommand

interface OnboardingNavigation {
  fun navigateToAuth(): NavigationCommand
}

class OnboardingNavigationImpl : OnboardingNavigation {

  override fun navigateToAuth(): NavigationCommand =
    OnboardingFragmentDirections.onboardingToAuthPolicy().toCommand()
}
