package ru.frogogo.whitelabel.ui.home.delegate

import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.extension.isEmpty
import ru.frogogo.whitelabel.ui.home.HomeEffect
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

private val errorState = emptyList<RecyclerViewItem>()

class HomeStateHandlerDelegate(
  dispatchersProvider: DispatchersProvider,
  private val mutableDataLive: MutableLiveData<List<RecyclerViewItem>>,
  private val mutableIsLoadingLive: MutableLiveData<Boolean>,
  private val effectLiveEvent: LiveEvent<HomeEffect>,
) : BaseViewModelDelegate(dispatchersProvider) {

  fun showLoader() {
    mutableIsLoadingLive.postValue(mutableDataLive.isEmpty() || mutableDataLive.value == errorState)
  }

  fun showData(data: HomeState) {
    mutableIsLoadingLive.value = false
    mutableDataLive.value = listOf(data)
  }

  fun showError() {
    mutableIsLoadingLive.value = false
    effectLiveEvent.value = HomeEffect.ShowLoadingError
  }
}
