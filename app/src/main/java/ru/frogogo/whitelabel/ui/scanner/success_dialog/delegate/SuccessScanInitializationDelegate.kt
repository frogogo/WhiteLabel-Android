package ru.frogogo.whitelabel.ui.scanner.success_dialog.delegate

import androidx.lifecycle.MutableLiveData
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class SuccessScanInitializationDelegate(
  dispatchersProvider: DispatchersProvider,
  private val mutableReceiptLive: MutableLiveData<ReceiptUiModel>,
  private val receipt: ReceiptUiModel,
) : BaseViewModelDelegate(dispatchersProvider) {

  fun init() {
    mutableReceiptLive.value = receipt
  }
}
