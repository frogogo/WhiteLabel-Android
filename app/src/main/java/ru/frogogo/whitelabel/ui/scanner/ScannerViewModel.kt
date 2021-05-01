package ru.frogogo.whitelabel.ui.scanner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.cash.exhaustive.Exhaustive
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.extension.asLiveData
import ru.frogogo.whitelabel.extension.errorOrDefault
import ru.frogogo.whitelabel.usecase.receipt.CreateReceiptResult
import ru.frogogo.whitelabel.usecase.receipt.CreateReceiptUseCase
import ru.frogogo.whitelabel.util.QRCodeUtils
import ru.frogogo.whitelabel.util.ResourceProvider

class ScannerViewModel(
  private val navigation: ScannerNavigation,
  private val createReceiptUseCase: CreateReceiptUseCase,
  private val resourceProvider: ResourceProvider,
) : BaseViewModel() {

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive = _isLoadingLive.asLiveData()

  private val _errorLiveEvent = LiveEvent<String?>()
  val errorLiveEvent = _errorLiveEvent.asLiveData()

  fun handleQrString(string: String) {
    d { "Handling QR string - $string" }

    createReceipt(string)
  }

  fun navigateToHelp() {
    navigation.navigateToReceiptHelp()
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
          _errorLiveEvent.postValue(resourceProvider.errorOrDefault(result.error))
        }
      }
    }
  }
}
