package ru.frogogo.whitelabel.ui.scanner.delegate

import androidx.lifecycle.MutableLiveData
import app.cash.exhaustive.Exhaustive
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.extension.errorOrDefault
import ru.frogogo.whitelabel.ui.scanner.ScannerEffect
import ru.frogogo.whitelabel.usecase.receipt.CreateReceiptResult
import ru.frogogo.whitelabel.usecase.receipt.CreateReceiptUseCase
import ru.frogogo.whitelabel.util.ResourceProvider
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class ScannerCreateReceiptDelegateImpl(
  dispatchersProvider: DispatchersProvider,
  private val mutableIsLoadingLive: MutableLiveData<Boolean>,
  private val mutableEffectLiveEvent: LiveEvent<ScannerEffect>,
  private val createReceiptUseCase: CreateReceiptUseCase,
  private val receiptValidatorDelegate: ScannerReceiptValidatorDelegate,
  private val resourceProvider: ResourceProvider,
) : BaseViewModelDelegate(dispatchersProvider),
  ScannerCreateReceiptDelegate {

  override fun createReceipt(code: String) {
    if (!receiptValidatorDelegate.validate(code)) {
      return
    }

    scope.launch {
      mutableIsLoadingLive.value = true
      val result = createReceiptUseCase(code)
      handleResult(result)
      mutableIsLoadingLive.value = false
    }
  }

  private fun handleResult(result: CreateReceiptResult) {
    @Exhaustive
    when (result) {
      is CreateReceiptResult.Success -> {
        mutableEffectLiveEvent.value = ScannerEffect.ShowSuccess(result.receipt)
      }
      CreateReceiptResult.Error -> {
        val error = resourceProvider.getString(R.string.error_something_went_wrong)
        mutableEffectLiveEvent.value = ScannerEffect.ShowError(error)
      }
      is CreateReceiptResult.ValidationError -> {
        mutableEffectLiveEvent.value = ScannerEffect.ShowError(resourceProvider.errorOrDefault(result.error))
      }
    }
  }
}
