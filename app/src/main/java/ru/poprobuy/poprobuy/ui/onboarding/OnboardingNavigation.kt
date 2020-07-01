package ru.poprobuy.poprobuy.ui.onboarding

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface OnboardingNavigation {
  fun navigateToAuth(): NavigationCommand
}

class OnboardingNavigationImpl : OnboardingNavigation {

  override fun navigateToAuth(): NavigationCommand {
    return NavigationCommand.ById(R.id.onboarding_to_auth_policy)
  }

}
