package ru.poprobuy.poprobuy.usecase.receipt

import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import ru.poprobuy.poprobuy.data.model.api.getErrorOrDefault
import ru.poprobuy.poprobuy.data.repository.ReceiptsRepository
import ru.poprobuy.poprobuy.util.network.HttpStatus
import ru.poprobuy.poprobuy.util.network.NetworkResource
import ru.poprobuy.poprobuy.util.network.onHttpErrorWithCode

class CreateReceiptUseCase(
  private val receiptsRepository: ReceiptsRepository,
) {

  suspend operator fun invoke(qrString: String): CreateReceiptResult =
    when (val result = receiptsRepository.activateQrString(qrString)) {
      is NetworkResource.Success -> {
        i { "Receipt created successfully" }
        CreateReceiptResult.Success
      }
      is NetworkResource.Error -> {
        val error = result.error

        error.onHttpErrorWithCode(HttpStatus.UNPROCESSABLE_ENTITY_422) { errorResponse ->
          e { "Unprocessable entity while creating receipt" }
          return CreateReceiptResult.ValidationError(errorResponse.data.getErrorOrDefault())
        }

        e { "Generic error while creating receipt" }
        CreateReceiptResult.Error
      }
    }

}
