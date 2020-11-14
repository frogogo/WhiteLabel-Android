package ru.poprobuy.poprobuy.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.i
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.extension.asLiveData
import ru.poprobuy.poprobuy.extension.isEmpty
import ru.poprobuy.poprobuy.usecase.home.GetHomeUseCase
import ru.poprobuy.poprobuy.util.handle

class HomeViewModel(
  private val navigation: HomeNavigation,
  private val getHomeUseCase: GetHomeUseCase,
) : BaseViewModel() {

  private var refreshLooperJob: Job? = null
  private var isRefreshing: Boolean = false

  private val _dataLive = MutableLiveData<List<RecyclerViewItem>>()
  val dataLive = _dataLive.asLiveData()

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive = _isLoadingLive.asLiveData()

  private val _errorOccurredLiveEvent = LiveEvent<Unit>()
  val errorOccurredLiveEvent = _errorOccurredLiveEvent.asLiveData()

  override fun onStart() {
    super.onStart()
    isRefreshing = false

    i { "Starting refresh looper" }
    refreshLooperJob?.cancel()
    refreshLooperJob = viewModelScope.launch(Dispatchers.IO) {
      while (true) {
        refreshDataImpl()
        delay(REFRESH_DELAY)
      }
    }
  }

  override fun onStop() {
    super.onStop()
    i { "Finishing refresh looper" }
    refreshLooperJob?.cancel()
    isRefreshing = false
  }

  override fun onCleared() {
    super.onCleared()
    i { "Finishing refresh looper" }
    refreshLooperJob?.cancel()
    isRefreshing = false
  }

  fun refreshData() {
    viewModelScope.launch { refreshDataImpl() }
  }

  private suspend fun refreshDataImpl() {
    if (isRefreshing) return
    isRefreshing = true

    _isLoadingLive.postValue(_dataLive.isEmpty() || _dataLive.value == errorState)
    getHomeUseCase().handle(
      onSuccess = { _dataLive.postValue(listOf(it)) },
      onFailure = {
        _dataLive.postValue(errorState)
        _errorOccurredLiveEvent.postValue(Unit)
      }
    )
    _isLoadingLive.postValue(false)
    isRefreshing = false
  }

  fun navigateToProfile() {
    d { "Navigating to profile" }
    navigation.navigateToProfile().navigate()
  }

  fun navigateToReceiptScan() {
    d { "Navigating to receipt scan" }
    navigation.navigateToReceiptScan().navigate()
  }

  fun navigateToMachineEnter() {
    d { "Navigating to machine enter" }
    navigation.navigateToMachineEnter().navigate()
  }

  fun navigateToMachineScan() {
    d { "Navigating to machine scan" }
    navigation.navigateToMachineScan().navigate()
  }

  companion object {
    private const val REFRESH_DELAY = 5 * 1000L // 5 seconds
    private val errorState = emptyList<RecyclerViewItem>()
  }

}
