package ru.frogogo.whitelabel.data.repository

import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.machine.VendingMachineUiModel
import ru.frogogo.whitelabel.util.network.NetworkError

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
