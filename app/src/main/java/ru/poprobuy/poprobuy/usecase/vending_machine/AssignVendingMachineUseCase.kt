package ru.poprobuy.poprobuy.usecase.vending_machine

import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.data.repository.VendingMachineRepository
import ru.poprobuy.poprobuy.util.Result
import ru.poprobuy.poprobuy.util.network.HttpStatus
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.onHttpErrorWithCode

class AssignVendingMachineUseCase(
  private val vendingMachineRepository: VendingMachineRepository,
) {

  suspend operator fun invoke(
    machineId: String,
    receiptId: Int,
  ): AssignVendingMachineUseCaseResult {
    return when (val result = vendingMachineRepository.assignMachine(machineId, receiptId)) {
      is Result.Success -> handleSuccess(result.value)
      is Result.Failure -> handleFailure(result.error)
    }
  }

  private fun handleSuccess(
    result: VendingMachineUiModel,
  ): AssignVendingMachineUseCaseResult {
    return AssignVendingMachineUseCaseResult.Success(result)
  }

  private fun handleFailure(
    result: NetworkError<ErrorResponse>,
  ): AssignVendingMachineUseCaseResult {
    result.onHttpErrorWithCode(HttpStatus.UNPROCESSABLE_ENTITY_422) { error ->
      return AssignVendingMachineUseCaseResult.ValidationFailure("Error occurred") // TODO: 15.11.2020
    }

    result.onHttpErrorWithCode(HttpStatus.NOT_FOUND_404) {
      return AssignVendingMachineUseCaseResult.MachineNotFound
    }

    return AssignVendingMachineUseCaseResult.Failure
  }

}
