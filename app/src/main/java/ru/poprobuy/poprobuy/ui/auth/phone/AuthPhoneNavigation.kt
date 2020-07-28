package ru.poprobuy.poprobuy.ui.auth.phone

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface AuthPhoneNavigation {
  fun navigateToAuthCodeConfirmation(phoneNumber: String, passwordRefreshRate: Int): NavigationCommand
}

class AuthPhoneNavigationImpl : AuthPhoneNavigation {

  override fun navigateToAuthCodeConfirmation(phoneNumber: String, passwordRefreshRate: Int): NavigationCommand {
    val action = AuthPhoneFragmentDirections.authPhoneToAuthCodeConfirmation(phoneNumber, passwordRefreshRate)
    return NavigationCommand.ByAction(action)
  }

}
