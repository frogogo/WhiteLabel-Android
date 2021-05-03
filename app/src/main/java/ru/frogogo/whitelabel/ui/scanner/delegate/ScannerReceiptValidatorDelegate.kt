package ru.frogogo.whitelabel.ui.scanner.delegate

import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class ScannerReceiptValidatorDelegate(
  dispatchersProvider: DispatchersProvider,
) : BaseViewModelDelegate(dispatchersProvider) {

  fun validate(code: String): Boolean {

    return true
  }
}
