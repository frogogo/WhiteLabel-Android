package ru.frogogo.whitelabel.ui.auth.phone

import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.extension.toCommand

interface AuthPhoneNavigation {
  fun navigateToAuthCodeConfirmation(phoneNumber: String, passwordRefreshRate: Int): NavigationCommand
}

class AuthPhoneNavigationImpl : AuthPhoneNavigation {

  override fun navigateToAuthCodeConfirmation(phoneNumber: String, passwordRefreshRate: Int): NavigationCommand =
    AuthPhoneFragmentDirections.authPhoneToAuthCodeConfirmation(phoneNumber, passwordRefreshRate).toCommand()
}
