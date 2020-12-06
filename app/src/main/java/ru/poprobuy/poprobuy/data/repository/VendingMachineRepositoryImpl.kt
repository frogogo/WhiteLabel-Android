package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.data.mapper.toDomain
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.api.machine.TakeProductRequest
import ru.poprobuy.poprobuy.data.model.api.machine.VendingMachineAssignRequest
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.util.dispatcher.DispatchersProvider
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.apiCall
import ru.poprobuy.poprobuy.util.network.mapToResult

class VendingMachineRepositoryImpl(
  dispatcher: DispatchersProvider,
  private val api: PoprobuyApi,
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
