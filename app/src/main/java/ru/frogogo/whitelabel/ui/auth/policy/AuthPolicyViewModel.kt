package ru.frogogo.whitelabel.ui.auth.policy

import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.data.repository.AuthRepository

class AuthPolicyViewModel(
  private val authRepository: AuthRepository,
  private val navigation: AuthPolicyNavigation,
) : BaseViewModel() {

  fun navigateNext() {
    authRepository.setPolicyAccepted()
    navigation.navigateToAuthPhoneEnter().navigate()
  }
}
