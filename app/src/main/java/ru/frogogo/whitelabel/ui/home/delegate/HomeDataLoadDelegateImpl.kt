package ru.frogogo.whitelabel.ui.home.delegate

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.frogogo.whitelabel.core.handle
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.extension.isEmpty
import ru.frogogo.whitelabel.usecase.home.GetHomeUseCase
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

private val errorState = emptyList<RecyclerViewItem>()

class HomeDataLoadDelegateImpl(
  dispatchersProvider: DispatchersProvider,
  private val mutableDataLive: MutableLiveData<List<RecyclerViewItem>>,
  private val mutableIsLoadingLive: MutableLiveData<Boolean>,
  private val getHomeUseCase: GetHomeUseCase,
) : BaseViewModelDelegate(dispatchersProvider),
  HomeDataLoadDelegate {

  override fun refreshData() {
    scope.launch {
      mutableIsLoadingLive.postValue(mutableDataLive.isEmpty() || mutableDataLive.value == errorState)
      val result = getHomeUseCase()
      mutableIsLoadingLive.postValue(false)

      result.handle(
        onSuccess = { mutableDataLive.postValue(listOf(it)) },
        onFailure = {
          mutableDataLive.postValue(errorState)

          // TODO: 01.05.2021 Use effect
          // _errorOccurredLiveEvent.postValue(Unit)
        }
      )
    }
  }
}
