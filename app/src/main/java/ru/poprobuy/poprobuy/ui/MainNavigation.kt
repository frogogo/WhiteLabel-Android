package ru.poprobuy.poprobuy.ui

import ru.poprobuy.poprobuy.MainNavigationDirections
import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.extension.toCommand

interface MainNavigation {
  fun navigateToLoginDestructive(): NavigationCommand
}

class MainNavigationImpl : MainNavigation {

  override fun navigateToLoginDestructive(): NavigationCommand =
    MainNavigationDirections.actionGlobalLoginDestructive(showLogoutDialog = true).toCommand()

}
