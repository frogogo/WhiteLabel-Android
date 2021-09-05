package ru.frogogo.whitelabel.ui.promotion_items.delegate

import kotlinx.coroutines.launch
import ru.frogogo.whitelabel.core.handle
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.usecase.GetItemsUseCase
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class PromotionItemsDataLoadDelegate(
  dispatchersProvider: DispatchersProvider,
  private val getItemsUseCase: GetItemsUseCase,
  private val stateHandlerDelegate: PromotionItemsStateHandlerDelegate,
) : BaseViewModelDelegate(dispatchersProvider) {

  fun load() {
    scope.launch {
      stateHandlerDelegate.showLoader()
      getItemsUseCase().handle(
        onSuccess = stateHandlerDelegate::showData,
        onFailure = { stateHandlerDelegate.showError() },
      )
    }
  }
}
