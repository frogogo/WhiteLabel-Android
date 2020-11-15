package ru.poprobuy.poprobuy.ui.machine_select

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.i
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.extension.asLiveData
import ru.poprobuy.poprobuy.extension.exhaustive
import ru.poprobuy.poprobuy.usecase.vending_machine.AssignVendingMachineUseCase
import ru.poprobuy.poprobuy.usecase.vending_machine.AssignVendingMachineUseCaseResult
import ru.poprobuy.poprobuy.util.ResourceProvider
import ru.poprobuy.poprobuy.util.Validators

class MachineSelectViewModel(
  private val receiptId: Int,
  private val navigation: MachineSelectNavigation,
  private val resourceProvider: ResourceProvider,
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
      _isLoadingLive.postValue(true)
      val result = assignVendingMachineUseCase(
        machineId = machineNumber,
        receiptId = receiptId
      )
      _isLoadingLive.postValue(false)

      when (result) {
        is AssignVendingMachineUseCaseResult.Success -> {
          hideKeyboard()
          navigation.navigateToProducts(result.vendingMachine).navigate()
        }
        is AssignVendingMachineUseCaseResult.ValidationFailure -> {
          _commandLiveEvent.postValue(MachineSelectCommand.DialogError(result.error))
        }
        AssignVendingMachineUseCaseResult.MachineNotFound -> {
          _commandLiveEvent.postValue(
            MachineSelectCommand.DialogError(resourceProvider.getString(R.string.machine_select_error_not_found))
          )
        }
        AssignVendingMachineUseCaseResult.Failure -> {
          _commandLiveEvent.postValue(MachineSelectCommand.DialogError(null))
        }
      }.exhaustive
    }
  }

  private fun validateNumber(number: String): Boolean {
    val error = Validators.isVendingMachineNumber(number)
    _commandLiveEvent.postValue(MachineSelectCommand.NumberValidationError(error))

    return error == null
  }
}
