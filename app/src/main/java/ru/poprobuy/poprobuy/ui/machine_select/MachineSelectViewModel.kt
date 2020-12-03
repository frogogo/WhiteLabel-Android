package ru.poprobuy.poprobuy.ui.machine_select

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.cash.exhaustive.Exhaustive
import com.github.ajalt.timberkt.i
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.ui.BaseViewModel
import ru.poprobuy.poprobuy.extension.asLiveData
import ru.poprobuy.poprobuy.extension.errorOrDefault
import ru.poprobuy.poprobuy.usecase.vending_machine.AssignVendingMachineUseCase
import ru.poprobuy.poprobuy.usecase.vending_machine.AssignVendingMachineUseCaseResult
import ru.poprobuy.poprobuy.util.ResourceProvider
import ru.poprobuy.poprobuy.util.Validators

// TODO: 26.11.2020 Tests
class MachineSelectViewModel(
  private val receiptId: Int,
  private val resourceProvider: ResourceProvider,
  private val navigation: MachineSelectNavigation,
  private val assignVendingMachineUseCase: AssignVendingMachineUseCase,
) : BaseViewModel() {

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive = _isLoadingLive.asLiveData()

  private val _commandLiveEvent = LiveEvent<MachineSelectCommand>()
  val commandLiveEvent = _commandLiveEvent.asLiveData()

  fun selectMachine(machineNumber: String) {
    if (!validateNumber(machineNumber)) return
    i { "Selecting machine - $machineNumber" }

    viewModelScope.launch {
      _isLoadingLive.value = true
      val result = assignVendingMachineUseCase(
        machineId = machineNumber,
        receiptId = receiptId
      )
      _isLoadingLive.value = false

      @Exhaustive
      when (result) {
        is AssignVendingMachineUseCaseResult.Success -> {
          hideKeyboard()
          navigation.navigateToProducts(receiptId = receiptId, vendingMachine = result.vendingMachine).navigate()
        }
        is AssignVendingMachineUseCaseResult.ValidationFailure -> {
          _commandLiveEvent.value = MachineSelectCommand.ShowDialogError(resourceProvider.errorOrDefault(result.error))
        }
        AssignVendingMachineUseCaseResult.MachineNotFound -> {
          _commandLiveEvent.value = MachineSelectCommand.ShowFieldError(R.string.machine_select_error_not_found)
        }
        AssignVendingMachineUseCaseResult.Failure -> {
          _commandLiveEvent.value = MachineSelectCommand.ShowDialogError(null)
        }
      }
    }
  }

  private fun validateNumber(number: String): Boolean {
    val error = Validators.isVendingMachineNumber(number)
    _commandLiveEvent.value = MachineSelectCommand.ShowFieldError(error)

    return error == null
  }
}
