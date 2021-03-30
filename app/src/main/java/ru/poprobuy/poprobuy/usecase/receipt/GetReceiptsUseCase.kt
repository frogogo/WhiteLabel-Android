package ru.poprobuy.poprobuy.usecase.receipt

import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel
import ru.poprobuy.poprobuy.data.repository.ReceiptsRepository
import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.util.network.NetworkError

class GetReceiptsUseCase(
  private val receiptsRepository: ReceiptsRepository,
) {

  suspend operator fun invoke(): Result<List<ReceiptUiModel>, NetworkError<ErrorResponse>> =
    receiptsRepository.getReceipts()
}
