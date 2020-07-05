package ru.poprobuy.poprobuy.ui.profile.receipts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.data.model.ui.ReceiptUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipts.ReceiptsScanAvailable
import ru.poprobuy.poprobuy.dictionary.ReceiptStatus
import java.util.*

class ReceiptsViewModel(
  private val navigation: ReceiptsNavigation
) : BaseViewModel() {

  private val _dataLive = MutableLiveData<List<RecyclerViewItem>>()
  val dataLive: MutableLiveData<List<RecyclerViewItem>> get() = _dataLive

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> get() = _isLoading

  init {
    viewModelScope.launch {
      _isLoading.postValue(true)
      val receipt = ReceiptUiModel(
        id = 123_123_123,
        value = 3499,
        date = Date(),
        shopName = "ВкусВилл",
        status = ReceiptStatus.ACCEPTED
      )

      delay(500)
      _isLoading.postValue(false)
      _dataLive.postValue(
        listOf(
          ReceiptsScanAvailable,
          receipt,
          receipt.copy(status = ReceiptStatus.REJECTED),
          receipt.copy(status = ReceiptStatus.CHECK)
        )
      )
    }
  }

  fun navigateToReceipt(receipt: ReceiptUiModel) {
    d { "Navigating to receipt details" }
    navigation.navigateToReceipt(receipt).navigate()
  }

}
