package ru.poprobuy.poprobuy.ui.auth.code

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.extension.toCommand

interface AuthCodeConfirmationNavigation {
  fun navigateToAuthName(): NavigationCommand
  fun navigateToApp(): NavigationCommand
}

class AuthCodeConfirmationNavigationImpl : AuthCodeConfirmationNavigation {

  override fun navigateToAuthName(): NavigationCommand =
    AuthCodeFragmentDirections.authCodeConfirmationToAuthName().toCommand()

  override fun navigateToApp(): NavigationCommand =
    AuthCodeFragmentDirections.authCodeConfirmationToHome().toCommand()
}
