package ru.frogogo.whitelabel.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.ui.home.delegate.HomeClickHandlerDelegate
import ru.frogogo.whitelabel.ui.home.delegate.HomeClickHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.home.delegate.HomeCouponReceivedDelegate
import ru.frogogo.whitelabel.ui.home.delegate.HomeDataLoadDelegate
import ru.frogogo.whitelabel.ui.home.delegate.HomeDataLoadDelegateImpl
import ru.frogogo.whitelabel.ui.home.delegate.HomeStateHandlerDelegate

class HomeViewModel(
  liveData: LiveDataHolder,
  private val delegates: DelegatesHolder,
) : BaseViewModel(),
  HomeClickHandlerDelegate by delegates.clicksHandlerDelegate,
  HomeDataLoadDelegate by delegates.dataLoadDelegate {

  val dataLive: LiveData<List<RecyclerViewItem>> = liveData.mutableDataLive
  val isLoadingLive: LiveData<Boolean> = liveData.mutableIsLoadingLive
  val effectLiveEvent: LiveData<HomeEffect> = liveData.mutableEffectLiveEvent
  val scanButtonStateLive: LiveData<HomeScanButtonState> = liveData.mutableScanButtonStateLive

  init {
    attachNavigatorDelegate(delegates.clicksHandlerDelegate)
  }

  override fun onStart() {
    super.onStart()
    refreshData()
  }

  override fun onCleared() {
    super.onCleared()
    delegates.apply {
      dataLoadDelegate.cancelJob()
      clicksHandlerDelegate.cancelJob()
      stateHandlerDelegate.cancelJob()
      couponReceivedDelegate.cancelJob()
    }
  }

  data class DelegatesHolder(
    val dataLoadDelegate: HomeDataLoadDelegateImpl,
    val clicksHandlerDelegate: HomeClickHandlerDelegateImpl,
    val stateHandlerDelegate: HomeStateHandlerDelegate,
    val couponReceivedDelegate: HomeCouponReceivedDelegate,
  )

  data class LiveDataHolder(
    val mutableDataLive: MutableLiveData<List<RecyclerViewItem>>,
    val mutableIsLoadingLive: MutableLiveData<Boolean>,
    val mutableEffectLiveEvent: LiveEvent<HomeEffect>,
    val mutableScanButtonStateLive: MutableLiveData<HomeScanButtonState>,
  )
}
