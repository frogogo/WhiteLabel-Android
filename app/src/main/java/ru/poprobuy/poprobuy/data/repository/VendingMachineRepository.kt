package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.util.network.NetworkError

interface VendingMachineRepository {

  suspend fun assignMachine(
    machineId: String,
    receiptId: Int,
  ): Result<VendingMachineUiModel, NetworkError<ErrorResponse>>

  suspend fun takeProduct(
    machineId: Int,
    receiptId: Int,
    productId: Int,
    vendingCellId: Int,
  ): Result<Unit, NetworkError<ErrorResponse>>

}
