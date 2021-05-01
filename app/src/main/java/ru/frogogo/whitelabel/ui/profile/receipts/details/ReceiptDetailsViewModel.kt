package ru.frogogo.whitelabel.ui.profile.receipts.details

import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.extension.asLiveData

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
}
