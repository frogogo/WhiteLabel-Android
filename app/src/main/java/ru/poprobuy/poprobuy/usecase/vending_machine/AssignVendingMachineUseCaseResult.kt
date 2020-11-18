package ru.poprobuy.poprobuy.usecase.vending_machine

import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel

sealed class AssignVendingMachineUseCaseResult {
  data class Success(val vendingMachine: VendingMachineUiModel) : AssignVendingMachineUseCaseResult()
  data class ValidationFailure(val error: String) : AssignVendingMachineUseCaseResult()
  object MachineNotFound : AssignVendingMachineUseCaseResult()
  object Failure : AssignVendingMachineUseCaseResult()
}
