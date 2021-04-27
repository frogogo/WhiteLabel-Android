package ru.frogogo.whitelabel.usecase.auth

import ru.frogogo.whitelabel.core.successOrNull
import ru.frogogo.whitelabel.data.repository.AuthRepository
import ru.frogogo.whitelabel.data.repository.UserRepository

class RefreshTokenUseCase(
  private val authRepository: AuthRepository,
  private val userRepository: UserRepository,
) {

  /**
   * @return new access token
   */
  suspend operator fun invoke(): String? {
    val refreshToken = authRepository.getRefreshToken() ?: return null
    val authData = authRepository
      .refreshToken(refreshToken)
      .successOrNull() ?: return null

    authRepository.saveAuthTokens(accessToken = authData.accessToken, refreshToken = authData.refreshToken)
    authData.user?.let { userRepository.saveUser(it) }

    return authData.accessToken
  }
}
