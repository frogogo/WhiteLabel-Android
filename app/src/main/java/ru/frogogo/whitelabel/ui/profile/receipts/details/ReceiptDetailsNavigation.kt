package ru.frogogo.whitelabel.ui.profile.receipts.details

import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.extension.toCommand

interface ReceiptDetailsNavigation {
  fun navigateToReceiptScan(): NavigationCommand
}

class ReceiptDetailsNavigationImpl : ReceiptDetailsNavigation {

  override fun navigateToReceiptScan(): NavigationCommand =
    ReceiptDetailsBottomDialogDirections.receiptDetailsToScanner().toCommand()
}
