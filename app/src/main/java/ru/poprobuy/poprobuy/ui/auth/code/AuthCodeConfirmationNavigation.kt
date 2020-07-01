package ru.poprobuy.poprobuy.ui.auth.code

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface AuthCodeConfirmationNavigation {
  fun navigateBack(): NavigationCommand
  fun navigateToAuthName(): NavigationCommand
}

class AuthCodeConfirmationNavigationImpl : AuthCodeConfirmationNavigation {

  override fun navigateBack(): NavigationCommand {
    return NavigationCommand.Back
  }

  override fun navigateToAuthName(): NavigationCommand {
    return NavigationCommand.ById(R.id.auth_code_confirmation_to_auth_name)
  }

}
