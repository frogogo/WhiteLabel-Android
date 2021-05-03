@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di.scope

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import ru.frogogo.whitelabel.ui.scanner.ScannerFragment
import ru.frogogo.whitelabel.ui.scanner.ScannerNavigation
import ru.frogogo.whitelabel.ui.scanner.ScannerNavigationImpl
import ru.frogogo.whitelabel.ui.scanner.ScannerViewModel
import ru.frogogo.whitelabel.ui.scanner.delegate.ScannerClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.scanner.delegate.ScannerClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.scanner.delegate.ScannerCreateReceiptDelegate
import ru.frogogo.whitelabel.ui.scanner.delegate.ScannerReceiptValidatorDelegate

fun Module.scanner() {
  scope<ScannerFragment> {
    // Core
    viewModel { ScannerViewModel(get(), get(), get()) }
    scoped { ScannerNavigationImpl() as ScannerNavigation }

    // LiveData

    // Delegates
    scoped {
      ScannerClicksHandlerDelegateImpl(get())
    } bind ScannerClicksHandlerDelegate::class
    scoped {
      ScannerCreateReceiptDelegate(get())
    }
    scoped {
      ScannerReceiptValidatorDelegate(get())
    }
  }
}
