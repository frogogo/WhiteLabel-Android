package ru.poprobuy.poprobuy.ui.auth.name

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.extension.toCommand

interface AuthNameNavigation {
  fun navigateToAuthEmail(userName: String): NavigationCommand
}

class AuthNameNavigationImpl : AuthNameNavigation {

  override fun navigateToAuthEmail(userName: String): NavigationCommand =
    AuthNameFragmentDirections.authNameToAuthEmail(userName).toCommand()

}
