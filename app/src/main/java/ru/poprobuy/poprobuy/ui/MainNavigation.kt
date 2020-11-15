package ru.poprobuy.poprobuy.ui

import ru.poprobuy.poprobuy.MainNavigationDirections
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface MainNavigation {
  fun navigateToLoginDestructive(): NavigationCommand
}

class MainNavigationImpl : MainNavigation {

  override fun navigateToLoginDestructive(): NavigationCommand {
    val action = MainNavigationDirections.actionGlobalLoginDestructive(showLogoutDialog = true)
    return NavigationCommand.ByAction(action)
  }

}
