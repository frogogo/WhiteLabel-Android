package ru.poprobuy.poprobuy.ui.auth.email

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface AuthEmailNavigation {
  fun navigateToApp(): NavigationCommand
}

class AuthEmailNavigationImpl : AuthEmailNavigation {

  override fun navigateToApp(): NavigationCommand {
    return NavigationCommand.ById(R.id.auth_email_to_home)
  }

}
