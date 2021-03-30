package ru.poprobuy.poprobuy.ui.profile.receipts.details

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.dictionary.ScanMode
import ru.poprobuy.poprobuy.extension.toCommand

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
