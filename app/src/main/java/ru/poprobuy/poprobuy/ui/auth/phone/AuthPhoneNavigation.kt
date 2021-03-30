package ru.poprobuy.poprobuy.ui.auth.phone

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.extension.toCommand

interface AuthPhoneNavigation {
  fun navigateToAuthCodeConfirmation(phoneNumber: String, passwordRefreshRate: Int): NavigationCommand
}

class AuthPhoneNavigationImpl : AuthPhoneNavigation {

  override fun navigateToAuthCodeConfirmation(phoneNumber: String, passwordRefreshRate: Int): NavigationCommand =
    AuthPhoneFragmentDirections.authPhoneToAuthCodeConfirmation(phoneNumber, passwordRefreshRate).toCommand()
}
