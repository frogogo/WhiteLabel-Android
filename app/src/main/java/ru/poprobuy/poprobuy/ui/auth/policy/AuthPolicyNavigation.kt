package ru.poprobuy.poprobuy.ui.auth.policy

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface AuthPolicyNavigation {
  fun navigateToAuthPhoneEnter(): NavigationCommand
}

class AuthPolicyNavigationImpl : AuthPolicyNavigation {

  override fun navigateToAuthPhoneEnter(): NavigationCommand {
    val action = AuthPolicyFragmentDirections.authPolicyToAuthPhone()
    return NavigationCommand.ByAction(action)
  }

}
