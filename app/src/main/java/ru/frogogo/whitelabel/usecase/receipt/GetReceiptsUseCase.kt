package ru.frogogo.whitelabel.usecase.receipt

import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.data.repository.ReceiptsRepository
import ru.frogogo.whitelabel.util.network.NetworkError

class GetReceiptsUseCase(
  private val receiptsRepository: ReceiptsRepository,
) {

  suspend operator fun invoke(): Result<List<ReceiptUiModel>, NetworkError<ErrorResponse>> =
    receiptsRepository.getReceipts()
}
