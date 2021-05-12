package ru.frogogo.whitelabel.ui.scanner

import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.extension.toCommand
import ru.frogogo.whitelabel.util.Constants

interface ScannerNavigation {
  fun navigateToReceiptHelp(): NavigationCommand
  fun navigateToHome(): NavigationCommand
}

class ScannerNavigationImpl : ScannerNavigation {

  override fun navigateToReceiptHelp(): NavigationCommand =
    NavigationCommand.ByWebUrl(
      url = Constants.HELP_SCAN_RECEIPT_URL,
      titleRes = R.string.scanner_help_receipt_title
    )

  override fun navigateToHome(): NavigationCommand =
    ScannerFragmentDirections.scannerToHome().toCommand()
}
