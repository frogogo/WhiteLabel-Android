package ru.frogogo.whitelabel.ui.promotion_items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.ui.promotion_items.delegate.PromotionItemsClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.promotion_items.delegate.PromotionItemsClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.promotion_items.delegate.PromotionItemsDataLoadDelegate
import ru.frogogo.whitelabel.ui.promotion_items.delegate.PromotionItemsStateHandlerDelegate

class PromotionItemsViewModel(
  liveData: LiveDataHolder,
  private val delegates: DelegatesHolder,
) : BaseViewModel(),
  PromotionItemsClicksHandlerDelegate by delegates.clicksHandlerDelegate {

  val dataLive: LiveData<List<RecyclerViewItem>> = liveData.mutableDataLive
  val isLoadingLive: LiveData<Boolean> = liveData.mutableIsLoadingLive
  val effectLiveEvent: LiveData<PromotionItemsEffect> = liveData.mutableEffectLiveEvent

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
    val mutableEffectLiveEvent: LiveEvent<PromotionItemsEffect>,
  )

  data class DelegatesHolder(
    val dataLoadDelegate: PromotionItemsDataLoadDelegate,
    val stateHandlerDelegate: PromotionItemsStateHandlerDelegate,
    val clicksHandlerDelegate: PromotionItemsClicksHandlerDelegateImpl,
  )
}
