package ru.poprobuy.poprobuy.ui.auth.code

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand

interface AuthCodeConfirmationNavigation {
  fun navigateToAuthName(): NavigationCommand
  fun navigateToApp(): NavigationCommand
}

class AuthCodeConfirmationNavigationImpl : AuthCodeConfirmationNavigation {

  override fun navigateToAuthName(): NavigationCommand {
    val action = AuthCodeFragmentDirections.authCodeConfirmationToAuthName()
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToApp(): NavigationCommand {
    val action = AuthCodeFragmentDirections.authCodeConfirmationToHome()
    return NavigationCommand.ByAction(action)
  }

}
