package ru.frogogo.whitelabel.ui

import ru.frogogo.whitelabel.MainNavigationDirections
import ru.frogogo.whitelabel.core.navigation.NavigationCommand

interface MainNavigation {
  fun navigateToLoginDestructive(): NavigationCommand
}

class MainNavigationImpl : MainNavigation {

  override fun navigateToLoginDestructive(): NavigationCommand =
    MainNavigationDirections.actionGlobalLoginDestructive(showLogoutDialog = true).toCommand()
}
