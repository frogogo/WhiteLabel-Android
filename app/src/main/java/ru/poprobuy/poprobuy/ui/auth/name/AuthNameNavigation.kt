package ru.poprobuy.poprobuy.ui.auth.name

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface AuthNameNavigation {
  fun navigateToAuthEmail(userName: String): NavigationCommand
}

class AuthNameNavigationImpl : AuthNameNavigation {

  override fun navigateToAuthEmail(userName: String): NavigationCommand {
    val action = AuthNameFragmentDirections.authNameToAuthEmail(userName)
    return NavigationCommand.ByAction(action)
  }

}
