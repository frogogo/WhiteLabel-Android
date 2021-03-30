package ru.poprobuy.poprobuy.ui.scanner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.cash.exhaustive.Exhaustive
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.ui.BaseViewModel
import ru.poprobuy.poprobuy.dictionary.ScanMode
import ru.poprobuy.poprobuy.extension.asLiveData
import ru.poprobuy.poprobuy.extension.errorOrDefault
import ru.poprobuy.poprobuy.usecase.receipt.CreateReceiptResult
import ru.poprobuy.poprobuy.usecase.receipt.CreateReceiptUseCase
import ru.poprobuy.poprobuy.usecase.vending_machine.AssignVendingMachineUseCase
import ru.poprobuy.poprobuy.usecase.vending_machine.AssignVendingMachineUseCaseResult
import ru.poprobuy.poprobuy.util.QRCodeUtils
import ru.poprobuy.poprobuy.util.ResourceProvider

class ScannerViewModel(
  private val scanMode: ScanMode,
  private val receiptId: Int,
  private val navigation: ScannerNavigation,
  private val createReceiptUseCase: CreateReceiptUseCase,
  private val assignVendingMachineUseCase: AssignVendingMachineUseCase,
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
      ScanMode.MACHINE -> assignMachine(string)
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
          _errorLiveEvent.postValue(resourceProvider.errorOrDefault(result.error))
        }
      }
    }
  }

  private fun assignMachine(string: String) {
    val number = QRCodeUtils.getVendingMachineNumber(string)
    if (number.isNullOrEmpty()) {
      e { "Machine QR-string is wrong - $string" }
      _errorLiveEvent.postValue(resourceProvider.getString(R.string.error_receipt_format))
      return
    }

    viewModelScope.launch {
      _isLoadingLive.postValue(true)
      val result = assignVendingMachineUseCase(number, receiptId)
      _isLoadingLive.postValue(false)

      @Exhaustive
      when (result) {
        is AssignVendingMachineUseCaseResult.Success -> {
          navigation.navigateToProducts(receiptId, result.vendingMachine).navigate()
        }
        is AssignVendingMachineUseCaseResult.ValidationFailure -> {
          _errorLiveEvent.postValue(result.error)
        }
        AssignVendingMachineUseCaseResult.MachineNotFound -> {
          _errorLiveEvent.postValue(resourceProvider.getString(R.string.machine_select_error_not_found))
        }
        AssignVendingMachineUseCaseResult.Failure -> {
          _errorLiveEvent.postValue(null)
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
