package ru.poprobuy.poprobuy.ui.auth.email

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface AuthEmailNavigation {
  fun navigateToApp(): NavigationCommand
}

class AuthEmailNavigationImpl : AuthEmailNavigation {

  override fun navigateToApp(): NavigationCommand {
    val action = AuthEmailFragmentDirections.authEmailToHome()
    return NavigationCommand.ByAction(action)
  }

}
