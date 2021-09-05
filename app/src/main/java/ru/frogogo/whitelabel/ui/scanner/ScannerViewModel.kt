package ru.frogogo.whitelabel.ui.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.ui.scanner.delegate.ScannerClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.scanner.delegate.ScannerClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.scanner.delegate.ScannerCreateReceiptDelegate
import ru.frogogo.whitelabel.ui.scanner.delegate.ScannerCreateReceiptDelegateImpl
import ru.frogogo.whitelabel.ui.scanner.delegate.ScannerReceiptValidatorDelegate

class ScannerViewModel(
  liveData: LiveDataHolder,
  private val delegates: DelegatesHolder,
) : BaseViewModel(),
  ScannerClicksHandlerDelegate by delegates.scannerClicksHandlerDelegate,
  ScannerCreateReceiptDelegate by delegates.createReceiptDelegate {

  val isLoadingLive: LiveData<Boolean> = liveData.mutableIsLoadingLive
  val effectEvent: LiveData<ScannerEffect> = liveData.mutableEffectLiveEvent

  init {
    attachNavigatorDelegate(delegates.scannerClicksHandlerDelegate)
  }

  override fun onCleared() {
    super.onCleared()
    delegates.apply {
      scannerClicksHandlerDelegate.cancelJob()
      createReceiptDelegate.cancelJob()
      receiptValidatorDelegate.cancelJob()
    }
  }

  class LiveDataHolder(
    val mutableEffectLiveEvent: LiveEvent<ScannerEffect>,
    val mutableIsLoadingLive: MutableLiveData<Boolean>,
  )

  class DelegatesHolder(
    val scannerClicksHandlerDelegate: ScannerClicksHandlerDelegateImpl,
    val createReceiptDelegate: ScannerCreateReceiptDelegateImpl,
    val receiptValidatorDelegate: ScannerReceiptValidatorDelegate,
  )
}
