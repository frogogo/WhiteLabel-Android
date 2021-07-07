package ru.frogogo.whitelabel.ui.promotion_items.delegate

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.frogogo.whitelabel.core.handle
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.ItemUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomePromotionUiModel
import ru.frogogo.whitelabel.ui.promotion_items.data.PromotionItemsDataFactory
import ru.frogogo.whitelabel.usecase.GetItemsUseCase
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider
import ru.frogogo.whitelabel.util.network.NetworkError

class PromotionItemsDataLoadDelegate(
  dispatchersProvider: DispatchersProvider,
  private val promotion: HomePromotionUiModel,
  private val mutableDataLive: MutableLiveData<List<RecyclerViewItem>>,
  private val getItemsUseCase: GetItemsUseCase,
  private val dataFactory: PromotionItemsDataFactory,
) : BaseViewModelDelegate(dispatchersProvider) {

  fun load() {
    scope.launch {
      getItemsUseCase().handle(
        onSuccess = ::handleSuccess,
        onFailure = ::handleFailure
      )
    }
  }

  private fun handleSuccess(items: List<ItemUiModel>) {
    mutableDataLive.value = dataFactory.create(promotion, items)
  }

  private fun handleFailure(error: NetworkError<ErrorResponse>) {

  }
}
