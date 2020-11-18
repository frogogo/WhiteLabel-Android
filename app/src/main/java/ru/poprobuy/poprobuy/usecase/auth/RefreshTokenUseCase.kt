package ru.poprobuy.poprobuy.usecase.auth

import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.util.successOrNull

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
    userRepository.saveUser(authData.user)

    return authData.accessToken
  }

}
