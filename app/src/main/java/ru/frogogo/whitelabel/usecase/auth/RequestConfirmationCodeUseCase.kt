package ru.frogogo.whitelabel.usecase.auth

import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.api.auth.ConfirmationCodeRequestResponse
import ru.frogogo.whitelabel.data.repository.AuthRepository
import ru.frogogo.whitelabel.util.network.HttpErrorReason
import ru.frogogo.whitelabel.util.network.NetworkError

class RequestConfirmationCodeUseCase(
  private val authRepository: AuthRepository,
) {

  suspend operator fun invoke(
    phoneNumber: String,
  ): RequestConfirmationResult = when (val result = authRepository.requestConfirmationCode(phoneNumber)) {
    is Result.Success -> handleSuccess(result.value)
    is Result.Failure -> handleFailure(result.error)
  }

  private fun handleSuccess(value: ConfirmationCodeRequestResponse): RequestConfirmationResult {
    i { "Code requested successfully" }
    return RequestConfirmationResult.Success(value.passwordRefreshRate)
  }

  private fun handleFailure(error: NetworkError<ErrorResponse>): RequestConfirmationResult {
    if (error is NetworkError.HttpError && error.data?.errorReason ==
      HttpErrorReason.ERROR_REASON_PASSWORD_REFRESH_RATE_LIMIT
    ) {
      return RequestConfirmationResult.TooManyRequests
    }

    e { "Generic error while requesting code" }
    return RequestConfirmationResult.Error
  }
}
