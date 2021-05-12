package ru.frogogo.whitelabel.ui.auth.email

import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.extension.toCommand

interface AuthEmailNavigation {
  fun navigateToApp(): NavigationCommand
}

class AuthEmailNavigationImpl : AuthEmailNavigation {

  override fun navigateToApp(): NavigationCommand =
    AuthEmailFragmentDirections.authEmailToHome().toCommand()
}
