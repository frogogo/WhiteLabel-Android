package ru.frogogo.whitelabel.ui.receipt_info.delegate

import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.ui.receipt_info.ReceiptInfoEffect
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class ReceiptInfoClicksHandlerDelegateImpl(
  dispatchersProvider: DispatchersProvider,
  private val mutableEffectEvent: LiveEvent<ReceiptInfoEffect>,
) : BaseViewModelDelegate(dispatchersProvider),
  ReceiptInfoClicksHandlerDelegate {

  override fun onNavigateBackClicked() {
    mutableEffectEvent.value = ReceiptInfoEffect.CloseDialog
  }
}
