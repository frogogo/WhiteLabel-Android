package ru.frogogo.whitelabel.ui.promotion_items.delegate

import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.data.model.ui.ItemUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomePromotionUiModel
import ru.frogogo.whitelabel.extension.isEmpty
import ru.frogogo.whitelabel.ui.promotion_items.PromotionItemsEffect
import ru.frogogo.whitelabel.ui.promotion_items.data.PromotionItemsDataFactory
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class PromotionItemsStateHandlerDelegate(
  dispatchersProvider: DispatchersProvider,
  private val promotion: HomePromotionUiModel,
  private val mutableDataLive: MutableLiveData<List<RecyclerViewItem>>,
  private val mutableIsLoadingLive: MutableLiveData<Boolean>,
  private val mutableEffectLiveEvent: LiveEvent<PromotionItemsEffect>,
  private val dataFactory: PromotionItemsDataFactory,
) : BaseViewModelDelegate(dispatchersProvider) {

  fun showLoader() {
    mutableIsLoadingLive.postValue(mutableDataLive.isEmpty())
  }

  fun showData(items: List<ItemUiModel>) {
    mutableIsLoadingLive.value = false
    mutableDataLive.value = dataFactory.create(promotion, items)
  }

  fun showError() {
    mutableIsLoadingLive.value = false
    mutableEffectLiveEvent.value = PromotionItemsEffect.ShowLoadingError
  }
}
