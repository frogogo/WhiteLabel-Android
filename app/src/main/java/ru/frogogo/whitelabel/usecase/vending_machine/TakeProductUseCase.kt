package ru.frogogo.whitelabel.usecase.vending_machine

import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.repository.VendingMachineRepository
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.util.network.NetworkError

class TakeProductUseCase(
  private val vendingMachineRepository: VendingMachineRepository,
) {

  suspend operator fun invoke(
    machineId: Int,
    receiptId: Int,
    productId: Int,
    vendingCellId: Int,
  ): Result<Unit, NetworkError<ErrorResponse>> = vendingMachineRepository.takeProduct(
    machineId = machineId,
    receiptId = receiptId,
    productId = productId,
    vendingCellId = vendingCellId,
  )
}
