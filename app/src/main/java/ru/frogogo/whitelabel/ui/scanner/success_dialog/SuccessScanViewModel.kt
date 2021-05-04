package ru.frogogo.whitelabel.ui.scanner.success_dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.ui.scanner.success_dialog.delegate.SuccessScanClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.scanner.success_dialog.delegate.SuccessScanClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.scanner.success_dialog.delegate.SuccessScanInitializationDelegate

class SuccessScanViewModel(
  liveData: LiveDataHolder,
  private val delegates: DelegatesHolder,
) : BaseViewModel(),
  SuccessScanClicksHandlerDelegate by delegates.clicksHandlerDelegate {

  val receiptLive: LiveData<ReceiptUiModel> = liveData.mutableReceiptLive
  val effectEvent: LiveData<SuccessScanEffect> = liveData.mutableEffectEvent

  override fun onCreate() {
    super.onCreate()
    delegates.initializationDelegate.init()
  }

  override fun onCleared() {
    super.onCleared()
    delegates.apply {
      initializationDelegate.cancelJob()
      clicksHandlerDelegate.cancelJob()
    }
  }

  class LiveDataHolder(
    val mutableReceiptLive: MutableLiveData<ReceiptUiModel>,
    val mutableEffectEvent: LiveEvent<SuccessScanEffect>,
  )

  class DelegatesHolder(
    val initializationDelegate: SuccessScanInitializationDelegate,
    val clicksHandlerDelegate: SuccessScanClicksHandlerDelegateImpl,
  )
}
