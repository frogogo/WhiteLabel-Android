package ru.frogogo.whitelabel.ui.auth.name

import ru.frogogo.whitelabel.core.navigation.NavigationCommand

interface AuthNameNavigation {
  fun navigateToAuthEmail(userName: String): NavigationCommand
}

class AuthNameNavigationImpl : AuthNameNavigation {

  override fun navigateToAuthEmail(userName: String): NavigationCommand =
    AuthNameFragmentDirections.authNameToAuthEmail(userName).toCommand()
}
