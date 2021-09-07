package ru.frogogo.whitelabel.ui.item_info.delegate

import kotlinx.coroutines.launch
import ru.frogogo.whitelabel.core.handle
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.usecase.item.GetItemUseCase
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class ItemInfoDataLoadDelegate(
  dispatchersProvider: DispatchersProvider,
  private val itemId: Int,
  private val getItemUseCase: GetItemUseCase,
  private val stateHandlerDelegate: ItemInfoStateHandlerDelegate,
) : BaseViewModelDelegate(dispatchersProvider) {

  fun load() {
    scope.launch {
      stateHandlerDelegate.showLoader()
      getItemUseCase(itemId).handle(
        onSuccess = stateHandlerDelegate::showData,
        onFailure = { stateHandlerDelegate.showError() },
      )
    }
  }
}
