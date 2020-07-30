package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.data.model.api.receipt.Receipt
import ru.poprobuy.poprobuy.data.model.api.receipt.ReceiptCreationRequest
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.util.network.NetworkResource
import ru.poprobuy.poprobuy.util.network.apiCall

class ReceiptsRepository(
  private val api: PoprobuyApi
) {

  suspend fun getReceipts(): NetworkResource<List<Receipt>, Any> {
    return apiCall { api.getReceipts() }
  }

  suspend fun activateQrString(qrString: String): NetworkResource<Unit, Any> {
    val request = ReceiptCreationRequest(qrString)
    return apiCall { api.createReceipt(request) }
  }

}
