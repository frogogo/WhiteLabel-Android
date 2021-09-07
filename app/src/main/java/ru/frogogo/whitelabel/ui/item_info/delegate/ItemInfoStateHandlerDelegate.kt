package ru.frogogo.whitelabel.ui.item_info.delegate

import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.data.model.ui.item.ItemInfoUiModel
import ru.frogogo.whitelabel.extension.isEmpty
import ru.frogogo.whitelabel.ui.item_info.ItemInfoEffect
import ru.frogogo.whitelabel.ui.item_info.data.ItemInfoDataFactory
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class ItemInfoStateHandlerDelegate(
  dispatchersProvider: DispatchersProvider,
  private val mutableDataLive: MutableLiveData<List<RecyclerViewItem>>,
  private val mutableIsLoadingLive: MutableLiveData<Boolean>,
  private val mutableEffectLiveEvent: LiveEvent<ItemInfoEffect>,
  private val dataFactory: ItemInfoDataFactory,
) : BaseViewModelDelegate(dispatchersProvider) {

  fun showLoader() {
    mutableIsLoadingLive.postValue(mutableDataLive.isEmpty())
  }

  fun showData(item: ItemInfoUiModel) {
    mutableIsLoadingLive.value = false
    // mutableDataLive.value = dataFactory.create(itemId, items)
  }

  fun showError() {
    mutableIsLoadingLive.value = false
    mutableEffectLiveEvent.value = ItemInfoEffect.ShowLoadingError
  }
}
