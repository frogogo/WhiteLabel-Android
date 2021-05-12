package ru.frogogo.whitelabel.data.repository

import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.mapper.toDomain
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.api.receipt.ReceiptCreationRequest
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.data.network.FrogogoApi
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider
import ru.frogogo.whitelabel.util.network.NetworkError
import ru.frogogo.whitelabel.util.network.apiCall
import ru.frogogo.whitelabel.util.network.mapToResult

class ReceiptsRepositoryImpl(
  dispatcher: DispatchersProvider,
  private val api: FrogogoApi,
) : Repository(dispatcher), ReceiptsRepository {

  override suspend fun getReceipts(): Result<List<ReceiptUiModel>, NetworkError<ErrorResponse>> = withIOContext {
    apiCall { api.getReceipts() }.mapToResult { receipts ->
      receipts.map { it.toDomain() }
    }
  }

  override suspend fun activateQrString(
    qrString: String,
  ): Result<ReceiptUiModel, NetworkError<ErrorResponse>> = withIOContext {
    val request = ReceiptCreationRequest(qrString)
    apiCall { api.createReceipt(request) }.mapToResult { receipt ->
      receipt.toDomain()
    }
  }
}
