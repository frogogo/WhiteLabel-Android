package ru.poprobuy.poprobuy.usecase.auth

import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.util.network.HttpStatus
import ru.poprobuy.poprobuy.util.network.NetworkResource

class AuthenticationUseCase(
  private val authRepository: AuthRepository
) {

  suspend operator fun invoke(
    phoneNumber: String,
    password: String
  ): AuthenticationResult = when (val result = authRepository.authenticate(phoneNumber, password)) {
    is NetworkResource.Success -> {
      i { "User authorized successfully" }
      authRepository.apply {
        saveAuthToken(result.data.accessToken)
        setUserAuthorized()
      }
      AuthenticationResult.Success(result.data.isNew)
    }
    is NetworkResource.Error -> {
      val error = result.error
      if (error.isHttpErrorWithCode(HttpStatus.NOT_FOUND_404)) {
        e { "User not found while performing authorization" }
        AuthenticationResult.NotFound
      } else {
        e { "Generic error while performing authorization" }
        AuthenticationResult.Error
      }
    }
  }

}
