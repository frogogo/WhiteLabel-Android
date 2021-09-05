@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di.scope

import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import ru.frogogo.whitelabel.ui.scanner.ScannerEffect
import ru.frogogo.whitelabel.ui.scanner.ScannerFragment
import ru.frogogo.whitelabel.ui.scanner.ScannerNavigation
import ru.frogogo.whitelabel.ui.scanner.ScannerNavigationImpl
import ru.frogogo.whitelabel.ui.scanner.ScannerViewModel
import ru.frogogo.whitelabel.ui.scanner.delegate.ScannerClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.scanner.delegate.ScannerClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.scanner.delegate.ScannerCreateReceiptDelegate
import ru.frogogo.whitelabel.ui.scanner.delegate.ScannerCreateReceiptDelegateImpl
import ru.frogogo.whitelabel.ui.scanner.delegate.ScannerReceiptValidatorDelegate

private const val NAMED_EFFECT_EVENT = "effect_event"
private const val NAMED_IS_LOADING_LIVE = "is_loading_live"

fun Module.scanner() {
  scope<ScannerFragment> {
    // Core
    viewModel { ScannerViewModel(get(), get()) }
    scoped { ScannerNavigationImpl() as ScannerNavigation }

    // LiveData
    scoped(named(NAMED_EFFECT_EVENT)) { LiveEvent<ScannerEffect>() }
    scoped(named(NAMED_IS_LOADING_LIVE)) { MutableLiveData<Boolean>() }
    scoped {
      ScannerViewModel.LiveDataHolder(
        mutableEffectLiveEvent = getEffectEvent(),
        mutableIsLoadingLive = getIsLoadingLive(),
      )
    }

    // Delegates
    scoped {
      ScannerClicksHandlerDelegateImpl(
        dispatchersProvider = get(),
        scannerNavigation = get(),
        mutableEffectEvent = getEffectEvent(),
      )
    } bind ScannerClicksHandlerDelegate::class
    scoped {
      ScannerCreateReceiptDelegateImpl(
        dispatchersProvider = get(),
        mutableIsLoadingLive = getIsLoadingLive(),
        mutableEffectLiveEvent = getEffectEvent(),
        createReceiptUseCase = get(),
        receiptValidatorDelegate = get(),
        resourceProvider = get(),

        )
    } bind ScannerCreateReceiptDelegate::class
    scoped {
      ScannerReceiptValidatorDelegate(
        dispatchersProvider = get(),
        mutableEffectLiveEvent = getEffectEvent(),
        resourceProvider = get(),
      )
    }
    scoped { ScannerViewModel.DelegatesHolder(get(), get(), get()) }
  }
}

private fun Scope.getEffectEvent(): LiveEvent<ScannerEffect> =
  get(named(NAMED_EFFECT_EVENT))

private fun Scope.getIsLoadingLive(): MutableLiveData<Boolean> =
  get(named(NAMED_IS_LOADING_LIVE))
