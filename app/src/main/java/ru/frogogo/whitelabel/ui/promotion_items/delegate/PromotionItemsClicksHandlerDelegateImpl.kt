package ru.frogogo.whitelabel.ui.promotion_items.delegate

import ru.frogogo.whitelabel.core.ui.AbstractViewModelNavigationDelegate
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class PromotionItemsClicksHandlerDelegateImpl(
  dispatchersProvider: DispatchersProvider,
) : AbstractViewModelNavigationDelegate(dispatchersProvider),
  PromotionItemsClicksHandlerDelegate {

  override fun onBackButtonClicked() {
    navigateBack()
  }
}
