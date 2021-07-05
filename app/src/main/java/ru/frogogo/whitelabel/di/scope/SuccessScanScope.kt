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
import ru.frogogo.whitelabel.ui.receipt_info.ReceiptInfoDialogFragment
import ru.frogogo.whitelabel.ui.receipt_info.ReceiptInfoEffect
import ru.frogogo.whitelabel.ui.receipt_info.ReceiptInfoViewModel
import ru.frogogo.whitelabel.ui.receipt_info.delegate.ReceiptInfoClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.receipt_info.delegate.ReceiptInfoClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.receipt_info.delegate.ReceiptInfoInitializationDelegate

private const val NAMED_RECEIPT = "receipt"
private const val NAMED_RECEIPT_LIVE = "receipt_live"
private const val NAMED_EFFECT_EVENT = "effect_event"

fun Module.receiptInfo() {
  scope<ReceiptInfoDialogFragment> {
    // Core
    viewModel { params ->
      loadBindingModule {
        single(scopedQualifier(NAMED_RECEIPT)) { params.get<ReceiptUiModel>() }
      }

      ReceiptInfoViewModel(get(), get())
    }

    // LiveData
    scoped(named(NAMED_RECEIPT_LIVE)) { MutableLiveData<ReceiptUiModel>() }
    scoped(named(NAMED_EFFECT_EVENT)) { LiveEvent<ReceiptInfoEffect>() }
    scoped {
      ReceiptInfoViewModel.LiveDataHolder(
        mutableReceiptLive = getReceiptLive(),
        mutableEffectEvent = getEffectEvent(),
      )
    }

    // Delegates
    scoped {
      ReceiptInfoClicksHandlerDelegateImpl(
        dispatchersProvider = get(),
        mutableEffectEvent = getEffectEvent()
      )
    } bind ReceiptInfoClicksHandlerDelegate::class
    scoped {
      ReceiptInfoInitializationDelegate(
        dispatchersProvider = get(),
        mutableReceiptLive = getReceiptLive(),
        receipt = get(scopedQualifier(NAMED_RECEIPT))
      )
    }
    scoped { ReceiptInfoViewModel.DelegatesHolder(get(), get()) }
  }
}

private fun Scope.getReceiptLive(): MutableLiveData<ReceiptUiModel> =
  get(named(NAMED_RECEIPT_LIVE))

private fun Scope.getEffectEvent(): LiveEvent<ReceiptInfoEffect> =
  get(named(NAMED_EFFECT_EVENT))
