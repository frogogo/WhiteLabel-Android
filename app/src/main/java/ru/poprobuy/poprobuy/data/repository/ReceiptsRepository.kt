package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel
import ru.poprobuy.poprobuy.util.network.NetworkError

interface ReceiptsRepository {

  suspend fun getReceipts(): Result<List<ReceiptUiModel>, NetworkError<ErrorResponse>>

  suspend fun activateQrString(qrString: String): Result<Unit, NetworkError<ErrorResponse>>

}
