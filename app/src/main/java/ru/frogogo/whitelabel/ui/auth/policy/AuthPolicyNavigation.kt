package ru.frogogo.whitelabel.ui.auth.policy

import ru.frogogo.whitelabel.core.navigation.NavigationCommand

interface AuthPolicyNavigation {
  fun navigateToAuthPhoneEnter(): NavigationCommand
}

class AuthPolicyNavigationImpl : AuthPolicyNavigation {

  override fun navigateToAuthPhoneEnter(): NavigationCommand =
    AuthPolicyFragmentDirections.authPolicyToAuthPhone().toCommand()
}
