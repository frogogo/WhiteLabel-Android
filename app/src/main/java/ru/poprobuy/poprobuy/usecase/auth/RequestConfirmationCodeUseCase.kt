package ru.poprobuy.poprobuy.usecase.auth

import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.util.network.HttpStatus
import ru.poprobuy.poprobuy.util.network.NetworkResource
import ru.poprobuy.poprobuy.util.network.onHttpErrorWithCode

class RequestConfirmationCodeUseCase(
  private val authRepository: AuthRepository
) {

  suspend operator fun invoke(
    phoneNumber: String
  ): RequestConfirmationResult = when (val result = authRepository.requestConfirmationCode(phoneNumber)) {
    is NetworkResource.Success -> {
      i { "Code requested successfully" }
      RequestConfirmationResult.Success(result.data.passwordRefreshRate)
    }
    is NetworkResource.Error -> {
      val error = result.error
      error.onHttpErrorWithCode(HttpStatus.TOO_MANY_REQUESTS_429) {
        e { "TooManyRequests while requesting code" }
        return RequestConfirmationResult.TooManyRequests
      }

      e { "Generic error while requesting code" }
      RequestConfirmationResult.Error
    }
  }

}
