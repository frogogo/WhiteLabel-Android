package ru.poprobuy.poprobuy.usecase.auth

import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.util.Result
import ru.poprobuy.poprobuy.util.network.HttpStatus
import ru.poprobuy.poprobuy.util.network.onHttpErrorWithCode

class AuthenticationUseCase(
  private val authRepository: AuthRepository,
) {

  suspend operator fun invoke(
    phoneNumber: String,
    password: String,
  ): AuthenticationResult = when (val result = authRepository.authenticate(phoneNumber, password)) {
    is Result.Success -> {
      i { "User authorized successfully" }
      authRepository.apply {
        saveAuthToken(result.value.accessToken)
        setUserAuthorized()
      }
      AuthenticationResult.Success(result.value.isNew)
    }
    is Result.Failure -> {
      val error = result.error
      error.onHttpErrorWithCode(HttpStatus.NOT_FOUND_404) {
        e { "User not found while performing authorization" }
        return AuthenticationResult.NotFound
      }

      e { "Generic error while performing authorization" }
      AuthenticationResult.Error
    }
  }

}
