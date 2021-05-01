package ru.frogogo.whitelabel.usecase.auth

import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.api.auth.AuthenticationResponse
import ru.frogogo.whitelabel.data.repository.AuthRepository
import ru.frogogo.whitelabel.data.repository.UserRepository
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.util.network.HttpStatus
import ru.frogogo.whitelabel.util.network.NetworkError
import ru.frogogo.whitelabel.util.network.onHttpErrorWithCode

class AuthenticationUseCase(
  private val authRepository: AuthRepository,
  private val userRepository: UserRepository,
) {

  suspend operator fun invoke(
    phoneNumber: String,
    password: String,
  ): AuthenticationResult = when (val result = authRepository.authenticate(phoneNumber, password)) {
    is Result.Success -> handleSuccess(result.value)
    is Result.Failure -> handleFailure(result.error)
  }

  private fun handleSuccess(
    data: AuthenticationResponse,
  ): AuthenticationResult {
    i { "User authorized successfully" }
    authRepository.saveAuthTokens(data.accessToken, data.refreshToken)
    // Don't set user authorized if isNew != false as he must enter account details (email and first name)
    // User can close the app so we need tor repeat all authorization process
    if (!data.isNew) {
      authRepository.setUserAuthorized()
    }
    data.user?.let { userRepository.saveUser(it) }

    return AuthenticationResult.Success(data.isNew)
  }

  private fun handleFailure(error: NetworkError<ErrorResponse>): AuthenticationResult {
    error.onHttpErrorWithCode(HttpStatus.NOT_FOUND_404) {
      e { "User not found while performing authorization" }
      return AuthenticationResult.NotFound
    }

    e { "Generic error while performing authorization" }
    return AuthenticationResult.Error
  }
}
