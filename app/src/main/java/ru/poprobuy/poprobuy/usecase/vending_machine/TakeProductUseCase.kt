package ru.poprobuy.poprobuy.usecase.vending_machine

import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.repository.VendingMachineRepository
import ru.poprobuy.poprobuy.util.Result
import ru.poprobuy.poprobuy.util.network.NetworkError

// TODO: 26.11.2020 Tests
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
