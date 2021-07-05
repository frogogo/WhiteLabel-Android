package ru.frogogo.whitelabel.ui.receipt_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.ui.receipt_info.delegate.ReceiptInfoClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.receipt_info.delegate.ReceiptInfoClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.receipt_info.delegate.ReceiptInfoInitializationDelegate

class ReceiptInfoViewModel(
  liveData: LiveDataHolder,
  private val delegates: DelegatesHolder,
) : BaseViewModel(),
  ReceiptInfoClicksHandlerDelegate by delegates.clicksHandlerDelegate {

  val receiptLive: LiveData<ReceiptUiModel> = liveData.mutableReceiptLive
  val effectEvent: LiveData<ReceiptInfoEffect> = liveData.mutableEffectEvent

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
    val mutableEffectEvent: LiveEvent<ReceiptInfoEffect>,
  )

  class DelegatesHolder(
    val initializationDelegate: ReceiptInfoInitializationDelegate,
    val clicksHandlerDelegate: ReceiptInfoClicksHandlerDelegateImpl,
  )
}
