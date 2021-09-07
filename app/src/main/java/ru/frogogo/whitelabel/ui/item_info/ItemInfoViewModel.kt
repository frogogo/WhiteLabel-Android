package ru.frogogo.whitelabel.ui.item_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.ui.item_info.delegate.ItemInfoClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.item_info.delegate.ItemInfoClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.item_info.delegate.ItemInfoDataLoadDelegate
import ru.frogogo.whitelabel.ui.item_info.delegate.ItemInfoStateHandlerDelegate

class ItemInfoViewModel(
  liveData: LiveDataHolder,
  private val delegates: DelegatesHolder,
) : BaseViewModel(),
  ItemInfoClicksHandlerDelegate by delegates.clicksHandlerDelegate {

  val dataLive: LiveData<List<RecyclerViewItem>> = liveData.mutableDataLive
  val isLoadingLive: LiveData<Boolean> = liveData.mutableIsLoadingLive
  val effectLiveEvent: LiveData<ItemInfoEffect> = liveData.mutableEffectLiveEvent

  init {
    attachNavigatorDelegate(delegates.clicksHandlerDelegate)
  }

  override fun onCreate() {
    super.onCreate()
    delegates.dataLoadDelegate.load()
  }

  override fun onCleared() {
    super.onCleared()
    delegates.apply {
      dataLoadDelegate.cancelJob()
      stateHandlerDelegate.cancelJob()
      clicksHandlerDelegate.cancelJob()
    }
  }

  fun retry() {
    delegates.dataLoadDelegate.load()
  }

  data class LiveDataHolder(
    val mutableDataLive: MutableLiveData<List<RecyclerViewItem>>,
    val mutableIsLoadingLive: MutableLiveData<Boolean>,
    val mutableEffectLiveEvent: LiveEvent<ItemInfoEffect>,
  )

  data class DelegatesHolder(
    val dataLoadDelegate: ItemInfoDataLoadDelegate,
    val stateHandlerDelegate: ItemInfoStateHandlerDelegate,
    val clicksHandlerDelegate: ItemInfoClicksHandlerDelegateImpl,
  )
}
