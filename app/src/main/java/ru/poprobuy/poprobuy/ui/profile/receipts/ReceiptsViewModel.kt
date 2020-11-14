package ru.poprobuy.poprobuy.ui.profile.receipts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel
import ru.poprobuy.poprobuy.extension.asLiveData
import ru.poprobuy.poprobuy.extension.isEmpty
import ru.poprobuy.poprobuy.usecase.receipt.GetReceiptsUseCase
import ru.poprobuy.poprobuy.util.handle

class ReceiptsViewModel(
  private val navigation: ReceiptsNavigation,
  private val getReceiptsUseCase: GetReceiptsUseCase,
) : BaseViewModel() {

  private val _dataLive = MutableLiveData<List<RecyclerViewItem>>()
  val dataLive = _dataLive.asLiveData()

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive = _isLoadingLive.asLiveData()

  private val _errorOccurredLiveEvent = LiveEvent<Unit>()
  val errorOccurredLiveEvent = _errorOccurredLiveEvent.asLiveData()

  override fun onCreate() {
    loadReceipts()
  }

  fun loadReceipts() {
    viewModelScope.launch {
      _isLoadingLive.postValue(_dataLive.isEmpty())
      getReceiptsUseCase().handle(
        onSuccess = { _dataLive.postValue(ReceiptsDataFactory.createReceiptsData(it)) },
        onFailure = { _errorOccurredLiveEvent.postValue(Unit) }
      )
      _isLoadingLive.postValue(false)
    }
  }

  fun navigateToReceipt(receipt: ReceiptUiModel) {
    d { "Navigating to receipt details" }
    navigation.navigateToReceipt(receipt).navigate()
  }

}
