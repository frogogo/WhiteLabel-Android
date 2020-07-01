package ru.poprobuy.poprobuy.ui.auth.phone

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface AuthPhoneNavigation {
  fun navigateToAuthCodeConfirmation(phoneNumber: String): NavigationCommand
}

class AuthPhoneNavigationImpl : AuthPhoneNavigation {

  override fun navigateToAuthCodeConfirmation(phoneNumber: String): NavigationCommand {
    val action = AuthPhoneFragmentDirections.authPhoneToAuthCodeConfirmation(phoneNumber)
    return NavigationCommand.ByAction(action)
  }

}
