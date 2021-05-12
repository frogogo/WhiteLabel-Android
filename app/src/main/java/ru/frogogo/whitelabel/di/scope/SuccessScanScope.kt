package ru.frogogo.whitelabel.di.scope

import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.extension.loadBindingModule
import ru.frogogo.whitelabel.extension.scopedQualifier
import ru.frogogo.whitelabel.ui.scanner.success_dialog.SuccessScanDialog
import ru.frogogo.whitelabel.ui.scanner.success_dialog.SuccessScanEffect
import ru.frogogo.whitelabel.ui.scanner.success_dialog.SuccessScanViewModel
import ru.frogogo.whitelabel.ui.scanner.success_dialog.delegate.SuccessScanClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.scanner.success_dialog.delegate.SuccessScanClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.scanner.success_dialog.delegate.SuccessScanInitializationDelegate

private const val NAMED_RECEIPT = "receipt"
private const val NAMED_RECEIPT_LIVE = "receipt_live"
private const val NAMED_EFFECT_EVENT = "effect_event"

fun Module.successScan() {
  scope<SuccessScanDialog> {
    // Core
    viewModel { params ->
      loadBindingModule {
        single(scopedQualifier(NAMED_RECEIPT)) { params.get<ReceiptUiModel>() }
      }

      SuccessScanViewModel(get(), get())
    }

    // LiveData
    scoped(named(NAMED_RECEIPT_LIVE)) { MutableLiveData<ReceiptUiModel>() }
    scoped(named(NAMED_EFFECT_EVENT)) { LiveEvent<SuccessScanEffect>() }
    scoped {
      SuccessScanViewModel.LiveDataHolder(
        mutableReceiptLive = getReceiptLive(),
        mutableEffectEvent = getEffectEvent(),
      )
    }

    // Delegates
    scoped {
      SuccessScanClicksHandlerDelegateImpl(
        dispatchersProvider = get(),
        mutableEffectEvent = getEffectEvent()
      )
    } bind SuccessScanClicksHandlerDelegate::class
    scoped {
      SuccessScanInitializationDelegate(
        dispatchersProvider = get(),
        mutableReceiptLive = getReceiptLive(),
        receipt = get(scopedQualifier(NAMED_RECEIPT))
      )
    }
    scoped { SuccessScanViewModel.DelegatesHolder(get(), get()) }
  }
}

private fun Scope.getReceiptLive(): MutableLiveData<ReceiptUiModel> =
  get(named(NAMED_RECEIPT_LIVE))

private fun Scope.getEffectEvent(): LiveEvent<SuccessScanEffect> =
  get(named(NAMED_EFFECT_EVENT))
