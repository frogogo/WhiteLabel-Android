package ru.frogogo.whitelabel.ui.item_info.delegate

import ru.frogogo.whitelabel.core.ui.AbstractViewModelNavigationDelegate
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class ItemInfoClicksHandlerDelegateImpl(
  dispatchersProvider: DispatchersProvider,
) : AbstractViewModelNavigationDelegate(dispatchersProvider),
  ItemInfoClicksHandlerDelegate {

  override fun onBackButtonClicked() {
    navigateBack()
  }
}
