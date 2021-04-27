package ru.frogogo.whitelabel.data.repository

import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.mapper.toDomain
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.api.machine.TakeProductRequest
import ru.frogogo.whitelabel.data.model.api.machine.VendingMachineAssignRequest
import ru.frogogo.whitelabel.data.model.ui.machine.VendingMachineUiModel
import ru.frogogo.whitelabel.data.network.FrogogoApi
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider
import ru.frogogo.whitelabel.util.network.NetworkError
import ru.frogogo.whitelabel.util.network.apiCall
import ru.frogogo.whitelabel.util.network.mapToResult

class VendingMachineRepositoryImpl(
  dispatcher: DispatchersProvider,
  private val api: FrogogoApi,
) : Repository(dispatcher), VendingMachineRepository {

  override suspend fun assignMachine(
    machineId: String,
    receiptId: Int,
  ): Result<VendingMachineUiModel, NetworkError<ErrorResponse>> = withIOContext {
    val body = VendingMachineAssignRequest(receiptId)

    apiCall { api.assignVendingMachine(machineId, body) }
      .mapToResult { it.toDomain() }
  }

  override suspend fun takeProduct(
    machineId: Int,
    receiptId: Int,
    productId: Int,
    vendingCellId: Int,
  ): Result<Unit, NetworkError<ErrorResponse>> = withIOContext {
    val body = TakeProductRequest(
      itemId = productId,
      receiptId = receiptId,
      vendingCellId = vendingCellId,
    )

    apiCall { api.takeProduct(machineId = machineId, body) }.mapToResult()
  }
}
