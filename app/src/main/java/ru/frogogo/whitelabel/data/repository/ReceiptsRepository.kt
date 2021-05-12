package ru.frogogo.whitelabel.data.repository

import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.util.network.NetworkError

interface ReceiptsRepository {

  suspend fun getReceipts(): Result<List<ReceiptUiModel>, NetworkError<ErrorResponse>>

  suspend fun activateQrString(qrString: String): Result<ReceiptUiModel, NetworkError<ErrorResponse>>
}
