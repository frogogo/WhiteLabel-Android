package ru.frogogo.whitelabel.ui.profile.receipts.details

import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.dictionary.ScanMode

interface ReceiptDetailsNavigation {
  fun navigateToReceiptScan(): NavigationCommand
  fun navigateToMachineScan(receiptId: Int): NavigationCommand
  fun navigateToMachineEnter(receiptId: Int): NavigationCommand
}

class ReceiptDetailsNavigationImpl : ReceiptDetailsNavigation {

  override fun navigateToReceiptScan(): NavigationCommand =
    ReceiptDetailsBottomDialogDirections.receiptDetailsToScanner(mode = ScanMode.RECEIPT).toCommand()

  override fun navigateToMachineScan(receiptId: Int): NavigationCommand =
    ReceiptDetailsBottomDialogDirections.receiptDetailsToScanner(
      mode = ScanMode.MACHINE,
      receiptId = receiptId
    ).toCommand()

  override fun navigateToMachineEnter(receiptId: Int): NavigationCommand =
    ReceiptDetailsBottomDialogDirections.receiptDetailsToMachineSelect(receiptId).toCommand()
}
