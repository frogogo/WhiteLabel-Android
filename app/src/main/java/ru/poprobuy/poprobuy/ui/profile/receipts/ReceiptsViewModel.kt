package ru.poprobuy.poprobuy.ui.profile.receipts

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
import ru.poprobuy.poprobuy.extension.asLiveData
import java.util.*

class ReceiptsViewModel(
  private val navigation: ReceiptsNavigation
) : BaseViewModel() {

  private val _dataLive = MutableLiveData<List<RecyclerViewItem>>()
  val dataLive = _dataLive.asLiveData()

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive = _isLoadingLive.asLiveData()

  init {
    viewModelScope.launch {
      _isLoadingLive.postValue(true)
      val receipt = ReceiptUiModel(
        id = 123_123_123,
        value = 3499,
        date = Date(),
        shopName = "ВкусВилл",
        status = ReceiptStatus.ACCEPTED
      )

      delay(500)
      _isLoadingLive.postValue(false)
      _dataLive.postValue(
        listOf(
          ReceiptsScanAvailable,
          receipt,
          receipt.copy(status = ReceiptStatus.REJECTED),
          receipt.copy(status = ReceiptStatus.CHECK),
          receipt.copy(status = ReceiptStatus.COMPLETED)
        )
      )
    }
  }

  fun navigateToReceipt(receipt: ReceiptUiModel) {
    d { "Navigating to receipt details" }
    navigation.navigateToReceipt(receipt).navigate()
  }

}
