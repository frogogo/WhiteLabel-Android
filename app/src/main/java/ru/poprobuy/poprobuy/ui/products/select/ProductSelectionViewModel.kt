package ru.poprobuy.poprobuy.ui.products.select

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.i
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.core.ui.BaseViewModel
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingCellUiModel
import ru.poprobuy.poprobuy.extension.asLiveData
import ru.poprobuy.poprobuy.extension.errorOrDefault
import ru.poprobuy.poprobuy.extension.setEvent
import ru.poprobuy.poprobuy.usecase.vending_machine.TakeProductUseCase
import ru.poprobuy.poprobuy.core.Event
import ru.poprobuy.poprobuy.core.doOnFailure
import ru.poprobuy.poprobuy.core.doOnSuccess
import ru.poprobuy.poprobuy.util.ResourceProvider

// TODO: 26.11.2020 Tests
class ProductSelectionViewModel(
  private val params: Params,
  private val resourceProvider: ResourceProvider,
  private val takeProductUseCase: TakeProductUseCase,
  private val productSelectionInteractor: MachineProductSelectionInteractor,
) : BaseViewModel() {

  private val _stateLive = MutableLiveData<MachineProductSelectionState>()
  val stateLive = _stateLive.asLiveData()

  private val _commandLive = MutableLiveData<Event<MachineProductSelectionCommand>>()
  val commandLive = _commandLive.asLiveData()

  private var productWasTaken = false

  init {
    _stateLive.value = MachineProductSelectionState.Product(params.vendingCell.product)
  }

  fun takeProduct() {
    i { "Taking product" }
    viewModelScope.launch {
      _commandLive.setEvent(MachineProductSelectionCommand.SetCancelable(false))
      _stateLive.value = MachineProductSelectionState.Product(params.vendingCell.product, isLoading = true)

      takeProductUseCase(
        machineId = params.vendingMachineId,
        receiptId = params.receiptId,
        productId = params.vendingCell.product.id,
        vendingCellId = params.vendingCell.id
      ).doOnSuccess {
        productWasTaken = true
        _stateLive.value = MachineProductSelectionState.Success
      }.doOnFailure { error ->
        _commandLive.setEvent(MachineProductSelectionCommand.DismissDialog)
        productSelectionInteractor.issueCommand(
          MachineProductSelectionInteractor.Command.ShowErrorDialog(resourceProvider.errorOrDefault(error))
        )
      }
      _commandLive.setEvent(MachineProductSelectionCommand.SetCancelable(true))
    }
  }

  fun requestDismiss() {
    i { "Dismiss requested" }
    viewModelScope.launch {
      _commandLive.setEvent(MachineProductSelectionCommand.DismissDialog)
      if (productWasTaken) {
        productSelectionInteractor.issueCommand(MachineProductSelectionInteractor.Command.NavigateToHome)
      }
    }
  }

  data class Params(
    val receiptId: Int,
    val vendingMachineId: Int,
    val vendingCell: VendingCellUiModel,
  )
}
