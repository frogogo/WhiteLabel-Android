package ru.poprobuy.poprobuy.ui.scanner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.cash.exhaustive.Exhaustive
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.dictionary.ScanMode
import ru.poprobuy.poprobuy.extension.asLiveData
import ru.poprobuy.poprobuy.usecase.receipt.CreateReceiptResult
import ru.poprobuy.poprobuy.usecase.receipt.CreateReceiptUseCase
import ru.poprobuy.poprobuy.util.QRCodeUtils
import ru.poprobuy.poprobuy.util.ResourceProvider

class ScannerViewModel(
  private val scanMode: ScanMode,
  private val receiptId: Int,
  private val navigation: ScannerNavigation,
  private val createReceiptUseCase: CreateReceiptUseCase,
  private val resourceProvider: ResourceProvider,
) : BaseViewModel() {

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive = _isLoadingLive.asLiveData()

  private val _errorLiveEvent = LiveEvent<String>()
  val errorLiveEvent = _errorLiveEvent.asLiveData()

  fun handleQrString(string: String) {
    d { "Handling QR string - $string" }

    @Exhaustive
    when (scanMode) {
      ScanMode.RECEIPT -> createReceipt(string)
      ScanMode.MACHINE -> TODO()
    }
  }

  private fun createReceipt(string: String) {
    if (!QRCodeUtils.isQueryString(string)) {
      _errorLiveEvent.postValue(resourceProvider.getString(R.string.error_receipt_format))
      return
    }

    viewModelScope.launch {
      _isLoadingLive.postValue(true)
      val result = createReceiptUseCase(string)
      _isLoadingLive.postValue(false)

      @Exhaustive
      when (result) {
        CreateReceiptResult.Success -> {
          navigation.navigateToHome().navigate()
        }
        CreateReceiptResult.Error -> {
          _errorLiveEvent.postValue(resourceProvider.getString(R.string.error_something_went_wrong))
        }
        is CreateReceiptResult.ValidationError -> {
          _errorLiveEvent.postValue(resourceProvider.getString(result.errorRes))
        }
      }
    }
  }

  fun navigateToHelp() {
    d { "Navigating to help for scan mode - $scanMode" }
    when (scanMode) {
      ScanMode.RECEIPT -> navigation.navigateToReceiptHelp()
      ScanMode.MACHINE -> navigation.navigateToMachineHelp()
    }.navigate()
  }

  fun navigateToManualMachineEnter() {
    d { "Navigating to manual machine enter" }
    receiptId.takeIf { it != -1 }?.let { id ->
      navigation.navigateToManualMachineEnter(id).navigate()
    } ?: run {
      e { "Receipt wasn't set" }
    }
  }

}
