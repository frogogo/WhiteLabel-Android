package ru.frogogo.whitelabel.ui.profile.receipts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import ru.frogogo.whitelabel.core.handle
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.extension.asLiveData
import ru.frogogo.whitelabel.extension.isEmpty
import ru.frogogo.whitelabel.usecase.receipt.GetReceiptsUseCase

class ReceiptsViewModel(
  private val getReceiptsUseCase: GetReceiptsUseCase,
) : BaseViewModel() {

  private val _dataLive = MutableLiveData<List<RecyclerViewItem>>()
  val dataLive = _dataLive.asLiveData()

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive = _isLoadingLive.asLiveData()

  private val _effectEvent = LiveEvent<ReceiptsEffect>()
  val effectEvent = _effectEvent.asLiveData()

  private val receipts: MutableList<ReceiptUiModel> = mutableListOf()

  override fun onCreate() {
    loadReceipts()
  }

  fun loadReceipts() {
    viewModelScope.launch {
      _isLoadingLive.postValue(_dataLive.isEmpty())
      val result = getReceiptsUseCase()
      _isLoadingLive.postValue(false)
      result.handle(
        onSuccess = { receipts ->
          _dataLive.postValue(ReceiptsDataUtils.createReceiptsData(receipts))
          this@ReceiptsViewModel.receipts.apply {
            clear()
            addAll(receipts)
          }
        },
        onFailure = { _effectEvent.postValue(ReceiptsEffect.ShowError) },
      )
    }
  }

  fun navigateToReceipt(receipt: ReceiptUiModel) {
    d { "Navigating to receipt details" }
    _effectEvent.value = ReceiptsEffect.OpenReceiptInfoDialog(receipt)
  }
}
