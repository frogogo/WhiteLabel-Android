package ru.poprobuy.poprobuy.ui.auth.policy

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface AuthPolicyNavigation {
  fun navigateToAuthPhoneEnter(): NavigationCommand
}

class AuthPolicyNavigationImpl : AuthPolicyNavigation {

  override fun navigateToAuthPhoneEnter(): NavigationCommand {
    return NavigationCommand.ById(R.id.auth_policy_to_auth_phone)
  }

}
