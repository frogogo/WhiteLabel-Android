package ru.frogogo.whitelabel.usecase.vending_machine

import ru.frogogo.whitelabel.data.model.ui.machine.VendingMachineUiModel

sealed class AssignVendingMachineUseCaseResult {
  object Failure : AssignVendingMachineUseCaseResult()
  object MachineNotFound : AssignVendingMachineUseCaseResult()
  data class Success(val vendingMachine: VendingMachineUiModel) : AssignVendingMachineUseCaseResult()
  data class ValidationFailure(val error: String?) : AssignVendingMachineUseCaseResult()
}
