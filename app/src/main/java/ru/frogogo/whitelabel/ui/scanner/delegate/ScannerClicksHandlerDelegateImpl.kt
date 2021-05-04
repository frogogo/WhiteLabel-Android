package ru.frogogo.whitelabel.ui.scanner.delegate

import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.ui.AbstractViewModelNavigationDelegate
import ru.frogogo.whitelabel.ui.scanner.ScannerEffect
import ru.frogogo.whitelabel.ui.scanner.ScannerNavigation
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class ScannerClicksHandlerDelegateImpl(
  dispatchersProvider: DispatchersProvider,
  private val scannerNavigation: ScannerNavigation,
  private val mutableEffectEvent: LiveEvent<ScannerEffect>,
) : AbstractViewModelNavigationDelegate(dispatchersProvider),
  ScannerClicksHandlerDelegate {

  override fun onHelpButtonClicked() {
    scannerNavigation.navigateToReceiptHelp().navigate()
  }

  override fun onBackButtonClicked() {
    navigateBack()
  }

  override fun onFlashButtonClicked() {
    mutableEffectEvent.value = ScannerEffect.ToggleFlash
  }
}
