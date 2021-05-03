package ru.frogogo.whitelabel.ui.profile.coupons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.ui.profile.coupons.delegate.CouponsClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.profile.coupons.delegate.CouponsClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.profile.coupons.delegate.CouponsDataLoadDelegateImpl
import ru.frogogo.whitelabel.ui.profile.coupons.delegate.CouponsStateHandlerDelegate

class CouponsViewModel(
  liveData: LiveDataHolder,
  private val delegates: DelegatesHolder,
) : BaseViewModel(),
  CouponsClicksHandlerDelegate by delegates.clicksHandlerDelegate {

  val dataLive: LiveData<List<RecyclerViewItem>> = liveData.mutableDataLive
  val isLoadingLive: LiveData<Boolean> = liveData.mutableIsLoadingLive
  val effectLiveEvent: LiveData<CouponsEffect> = liveData.mutableEffectLiveEvent

  init {
    attachNavigatorDelegate(delegates.clicksHandlerDelegate)
  }

  override fun onCreate() {
    super.onCreate()
    delegates.dataLoadDelegate.refreshData()
  }

  override fun onCleared() {
    super.onCleared()
    delegates.apply {
      clicksHandlerDelegate.cancelJob()
      dataLoadDelegate.cancelJob()
      stateHandlerDelegate.cancelJob()
    }
  }

  class LiveDataHolder(
    val mutableDataLive: MutableLiveData<List<RecyclerViewItem>>,
    val mutableIsLoadingLive: MutableLiveData<Boolean>,
    val mutableEffectLiveEvent: LiveEvent<CouponsEffect>,
  )

  class DelegatesHolder(
    val clicksHandlerDelegate: CouponsClicksHandlerDelegateImpl,
    val dataLoadDelegate: CouponsDataLoadDelegateImpl,
    val stateHandlerDelegate: CouponsStateHandlerDelegate,
  )
}
