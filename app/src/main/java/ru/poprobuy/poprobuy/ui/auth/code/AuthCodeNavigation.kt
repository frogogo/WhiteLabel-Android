package ru.poprobuy.poprobuy.ui.auth.code

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface AuthCodeConfirmationNavigation {
  fun navigateToAuthName(): NavigationCommand
  fun navigateToApp(): NavigationCommand
}

class AuthCodeConfirmationNavigationImpl : AuthCodeConfirmationNavigation {

  override fun navigateToAuthName(): NavigationCommand {
    return NavigationCommand.ById(R.id.auth_code_confirmation_to_auth_name)
  }

  override fun navigateToApp(): NavigationCommand {
    return NavigationCommand.ById(R.id.auth_code_confirmation_to_home)
  }

}
