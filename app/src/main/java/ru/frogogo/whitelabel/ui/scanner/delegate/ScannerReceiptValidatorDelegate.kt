package ru.frogogo.whitelabel.ui.scanner.delegate

import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.ui.scanner.ScannerEffect
import ru.frogogo.whitelabel.util.QRCodeUtils
import ru.frogogo.whitelabel.util.ResourceProvider
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class ScannerReceiptValidatorDelegate(
  dispatchersProvider: DispatchersProvider,
  private val mutableEffectLiveEvent: LiveEvent<ScannerEffect>,
  private val resourceProvider: ResourceProvider,
) : BaseViewModelDelegate(dispatchersProvider) {

  fun validate(code: String): Boolean {
    if (!QRCodeUtils.isQueryString(code)) {
      mutableEffectLiveEvent.value = ScannerEffect.ShowError(resourceProvider.getString(R.string.error_receipt_format))
      return false
    }
    return true
  }
}
