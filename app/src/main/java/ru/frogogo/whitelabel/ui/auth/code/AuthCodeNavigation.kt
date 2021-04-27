package ru.frogogo.whitelabel.ui.auth.code

import ru.frogogo.whitelabel.core.navigation.NavigationCommand

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
