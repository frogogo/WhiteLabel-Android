package ru.frogogo.whitelabel.ui.auth.policy

import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.extension.toCommand

interface AuthPolicyNavigation {
  fun navigateToAuthPhoneEnter(): NavigationCommand
}

class AuthPolicyNavigationImpl : AuthPolicyNavigation {

  override fun navigateToAuthPhoneEnter(): NavigationCommand =
    AuthPolicyFragmentDirections.authPolicyToAuthPhone().toCommand()
}
