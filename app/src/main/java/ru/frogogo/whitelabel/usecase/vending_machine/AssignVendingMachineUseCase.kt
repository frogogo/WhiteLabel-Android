package ru.frogogo.whitelabel.usecase.vending_machine

import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.machine.VendingMachineUiModel
import ru.frogogo.whitelabel.data.repository.VendingMachineRepository
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.util.network.HttpStatus
import ru.frogogo.whitelabel.util.network.NetworkError
import ru.frogogo.whitelabel.util.network.onHttpErrorWithCode

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
    i { "Machine assigned" }
    return AssignVendingMachineUseCaseResult.Success(result)
  }

  private fun handleFailure(
    result: NetworkError<ErrorResponse>,
  ): AssignVendingMachineUseCaseResult {
    result.onHttpErrorWithCode(HttpStatus.UNPROCESSABLE_ENTITY_422) { error ->
      e { "Machine assign request failed, $error returned" }
      return AssignVendingMachineUseCaseResult.ValidationFailure(error.data?.errorText)
    }

    result.onHttpErrorWithCode(HttpStatus.NOT_FOUND_404) {
      e { "Machine was not found" }
      return AssignVendingMachineUseCaseResult.MachineNotFound
    }

    e { "Generic failure occurred" }
    return AssignVendingMachineUseCaseResult.Failure
  }
}
