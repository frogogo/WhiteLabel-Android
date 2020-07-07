package ru.poprobuy.poprobuy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.data.model.ui.ReceiptUiModel
import ru.poprobuy.poprobuy.data.model.ui.home.HomeState
import ru.poprobuy.poprobuy.dictionary.ReceiptStatus
import java.util.*

class HomeViewModel(
  private val navigation: HomeNavigation
) : BaseViewModel() {

  private val _dataLive = MutableLiveData<List<RecyclerViewItem>>()
  val dataLive: LiveData<List<RecyclerViewItem>> get() = _dataLive

  init {
    val receipt = ReceiptUiModel(
      id = 123_123_123,
      value = 3499,
      date = Date(),
      shopName = "ВкусВилл",
      status = ReceiptStatus.ACCEPTED
    )

    viewModelScope.launch {
      delay(500)
      _dataLive.postValue(
        listOf(
          HomeState.Empty,
          HomeState.Receipt(receipt),
          HomeState.Receipt(receipt.copy(status = ReceiptStatus.REJECTED)),
          HomeState.Receipt(receipt.copy(status = ReceiptStatus.CHECK))
        )
      )
    }
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

}
