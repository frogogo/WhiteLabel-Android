package ru.poprobuy.poprobuy.ui.auth.policy

import ru.poprobuy.poprobuy.core.ui.BaseViewModel
import ru.poprobuy.poprobuy.data.repository.AuthRepository

class AuthPolicyViewModel(
  private val authRepository: AuthRepository,
  private val navigation: AuthPolicyNavigation,
) : BaseViewModel() {

  fun navigateNext() {
    authRepository.setPolicyAccepted()
    navigation.navigateToAuthPhoneEnter().navigate()
  }
}
