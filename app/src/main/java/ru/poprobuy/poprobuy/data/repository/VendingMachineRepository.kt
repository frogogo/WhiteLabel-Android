package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.data.mapper.toDomain
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.api.machine.TakeProductRequest
import ru.poprobuy.poprobuy.data.model.api.machine.VendingMachineAssignRequest
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.util.Result
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.apiCall
import ru.poprobuy.poprobuy.util.network.mapToResult

class VendingMachineRepository(
  private val api: PoprobuyApi,
) {

  suspend fun assignMachine(
    machineId: String,
    receiptId: Int,
  ): Result<VendingMachineUiModel, NetworkError<ErrorResponse>> {
    val body = VendingMachineAssignRequest(receiptId)

    return apiCall { api.assignVendingMachine(machineId, body) }
      .mapToResult { it.toDomain() }
  }

  suspend fun takeProduct(
    machineId: Int,
    receiptId: Int,
    productId: Int,
    vendingCellId: Int,
  ): Result<Unit, NetworkError<ErrorResponse>> {
    val body = TakeProductRequest(
      itemId = productId,
      receiptId = receiptId,
      vendingCellId = vendingCellId,
    )

    return apiCall { api.takeProduct(machineId = machineId, body) }.mapToResult()
  }

}
