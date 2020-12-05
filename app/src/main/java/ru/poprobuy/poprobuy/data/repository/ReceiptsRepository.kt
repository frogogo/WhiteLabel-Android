package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.data.mapper.toDomain
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.api.receipt.ReceiptCreationRequest
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.util.Result
import ru.poprobuy.poprobuy.util.dispatcher.DispatchersProvider
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.apiCall
import ru.poprobuy.poprobuy.util.network.mapToResult

class ReceiptsRepository(
  dispatcher: DispatchersProvider,
  private val api: PoprobuyApi,
) : Repository(dispatcher) {

  suspend fun getReceipts(): Result<List<ReceiptUiModel>, NetworkError<ErrorResponse>> = withIOContext {
    apiCall { api.getReceipts() }.mapToResult { receipts ->
      receipts.map { it.toDomain() }
    }
  }

  suspend fun activateQrString(qrString: String): Result<Unit, NetworkError<ErrorResponse>> = withIOContext {
    val request = ReceiptCreationRequest(qrString)
    apiCall { api.createReceipt(request) }.mapToResult()
  }

}
