package ru.frogogo.whitelabel.usecase.receipt

import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.data.repository.ReceiptsRepository
import ru.frogogo.whitelabel.util.network.HttpStatus
import ru.frogogo.whitelabel.util.network.NetworkError
import ru.frogogo.whitelabel.util.network.onHttpErrorWithCode

class CreateReceiptUseCase(
  private val receiptsRepository: ReceiptsRepository,
) {

  suspend operator fun invoke(qrString: String): CreateReceiptResult =
    when (val result = receiptsRepository.activateQrString(qrString)) {
      is Result.Success -> handleSuccess(result.value)
      is Result.Failure -> handleFailure(result.error)
    }

  private fun handleSuccess(receipt: ReceiptUiModel): CreateReceiptResult {
    i { "Receipt created successfully" }
    return CreateReceiptResult.Success(receipt)
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
