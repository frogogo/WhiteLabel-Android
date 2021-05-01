package ru.frogogo.whitelabel.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.extension.asLiveData
import ru.frogogo.whitelabel.ui.home.delegate.HomeClickHandlerDelegate
import ru.frogogo.whitelabel.ui.home.delegate.HomeClickHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.home.delegate.HomeDataLoadDelegate
import ru.frogogo.whitelabel.ui.home.delegate.HomeDataLoadDelegateImpl

class HomeViewModel(
  liveData: LiveDataHolder,
  private val delegates: DelegatesHolder,
) : BaseViewModel(),
  HomeClickHandlerDelegate by delegates.clicksHandlerDelegate,
  HomeDataLoadDelegate by delegates.dataLoadDelegate {

  val dataLive: LiveData<List<RecyclerViewItem>> = liveData.mutableDataLive
  val isLoadingLive: LiveData<Boolean> = liveData.mutableIsLoadingLive

  private val _errorOccurredLiveEvent = LiveEvent<Unit>()
  val errorOccurredLiveEvent = _errorOccurredLiveEvent.asLiveData()

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
    }
  }

  data class DelegatesHolder(
    val dataLoadDelegate: HomeDataLoadDelegateImpl,
    val clicksHandlerDelegate: HomeClickHandlerDelegateImpl,
  )

  data class LiveDataHolder(
    val mutableDataLive: MutableLiveData<List<RecyclerViewItem>>,
    val mutableIsLoadingLive: MutableLiveData<Boolean>,
  )
}
