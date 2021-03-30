package ru.poprobuy.poprobuy.usecase.receipt

import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.repository.ReceiptsRepository
import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.util.network.HttpStatus
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.onHttpErrorWithCode

class CreateReceiptUseCase(
  private val receiptsRepository: ReceiptsRepository,
) {

  suspend operator fun invoke(qrString: String): CreateReceiptResult =
    when (val result = receiptsRepository.activateQrString(qrString)) {
      is Result.Success -> handleSuccess()
      is Result.Failure -> handleFailure(result.error)
    }

  private fun handleSuccess(): CreateReceiptResult {
    i { "Receipt created successfully" }
    return CreateReceiptResult.Success
  }

  private fun handleFailure(error: NetworkError<ErrorResponse>): CreateReceiptResult {
    error.onHttpErrorWithCode(HttpStatus.UNPROCESSABLE_ENTITY_422) { errorResponse ->
      e { "Unprocessable entity while creating receipt" }
      return CreateReceiptResult.ValidationError(errorResponse.data?.errorText)
    }

    e { "Generic error while creating receipt" }
    return CreateReceiptResult.Error
  }
}
