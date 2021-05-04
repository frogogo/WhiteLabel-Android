package ru.frogogo.whitelabel.ui.scanner.success_dialog.delegate

import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.ui.scanner.success_dialog.SuccessScanEffect
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class SuccessScanClicksHandlerDelegateImpl(
  dispatchersProvider: DispatchersProvider,
  private val mutableEffectEvent: LiveEvent<SuccessScanEffect>,
) : BaseViewModelDelegate(dispatchersProvider),
  SuccessScanClicksHandlerDelegate {

  override fun onNavigateBackClicked() {
    mutableEffectEvent.value = SuccessScanEffect.CloseDialog
  }
}
