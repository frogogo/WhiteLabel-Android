package ru.poprobuy.poprobuy.ui.auth.email

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.extension.toCommand

interface AuthEmailNavigation {
  fun navigateToApp(): NavigationCommand
}

class AuthEmailNavigationImpl : AuthEmailNavigation {

  override fun navigateToApp(): NavigationCommand =
    AuthEmailFragmentDirections.authEmailToHome().toCommand()
}
