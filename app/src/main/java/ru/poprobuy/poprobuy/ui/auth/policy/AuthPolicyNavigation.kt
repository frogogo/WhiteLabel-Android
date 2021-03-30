package ru.poprobuy.poprobuy.ui.auth.policy

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.extension.toCommand

interface AuthPolicyNavigation {
  fun navigateToAuthPhoneEnter(): NavigationCommand
}

class AuthPolicyNavigationImpl : AuthPolicyNavigation {

  override fun navigateToAuthPhoneEnter(): NavigationCommand =
    AuthPolicyFragmentDirections.authPolicyToAuthPhone().toCommand()
}
