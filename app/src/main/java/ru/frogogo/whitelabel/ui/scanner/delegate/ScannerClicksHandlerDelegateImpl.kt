package ru.frogogo.whitelabel.ui.scanner.delegate

import ru.frogogo.whitelabel.core.ui.AbstractViewModelNavigationDelegate
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class ScannerClicksHandlerDelegateImpl(
  dispatchersProvider: DispatchersProvider,
) : AbstractViewModelNavigationDelegate(dispatchersProvider),
  ScannerClicksHandlerDelegate {

  override fun onHelpButtonClicked() {

  }

  override fun onBackButtonClicked() {

  }

  override fun onFlashButtonClicked() {

  }
}
