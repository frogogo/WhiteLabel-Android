package ru.poprobuy.poprobuy.usecase.auth

import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.util.network.HttpStatus
import ru.poprobuy.poprobuy.util.network.NetworkResource

class RequestConfirmationCodeUseCase(
  private val authRepository: AuthRepository
) {

  suspend operator fun invoke(
    phoneNumber: String
  ): RequestConfirmationResult = when (val result = authRepository.requestConfirmationCode(phoneNumber)) {
    is NetworkResource.Success -> {
      i { "Code requested successfully" }
      RequestConfirmationResult.Success
    }
    is NetworkResource.Error -> {
      val error = result.error
      if (error.isHttpErrorWithCode(HttpStatus.TOO_MANY_REQUESTS_429)) {
        e { "TooManyRequests while requesting code" }
        RequestConfirmationResult.TooManyRequests
      } else {
        e { "Generic error while requesting code" }
        RequestConfirmationResult.Error
      }
    }
  }

}
