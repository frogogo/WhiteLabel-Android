package ru.poprobuy.poprobuy.ui.profile.receipts.details

import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.ui.BaseViewModel
import ru.poprobuy.poprobuy.extension.asLiveData

class ReceiptDetailsViewModel(
  private val navigation: ReceiptDetailsNavigation,
  private val buttonState: ReceiptDetailsButtonState,
) : BaseViewModel() {

  private val _commandEvent = LiveEvent<ReceiptDetailsCommand>()
  val commandEvent = _commandEvent.asLiveData()

  fun navigateToReceiptScan() {
    if (!buttonState.canCreateReceipt) {
      _commandEvent.postValue(ReceiptDetailsCommand.ShowToast(R.string.receipt_details_error_create_unavailable))
      return
    }
    d { "Navigating to receipt scan" }
    navigation.navigateToReceiptScan().navigate()
  }

  fun navigateToMachineEnter(receiptId: Int) {
    if (!buttonState.canTakeProduct) {
      _commandEvent.postValue(ReceiptDetailsCommand.ShowToast(R.string.receipt_details_error_take_unavailable))
      return
    }
    d { "Navigating to machine enter" }
    navigation.navigateToMachineEnter(receiptId).navigate()
  }

  fun navigateToMachineScan(receiptId: Int) {
    if (!buttonState.canTakeProduct) {
      _commandEvent.postValue(ReceiptDetailsCommand.ShowToast(R.string.receipt_details_error_take_unavailable))
      return
    }
    d { "Navigating to machine scan" }
    navigation.navigateToMachineScan(receiptId).navigate()
  }

}
