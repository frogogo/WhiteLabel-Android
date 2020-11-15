package ru.poprobuy.poprobuy.ui.profile.receipts.details

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.dictionary.ScanMode

interface ReceiptDetailsNavigation {
  fun navigateToReceiptScan(): NavigationCommand
  fun navigateToMachineScan(): NavigationCommand
  fun navigateToMachineEnter(receiptId: Int): NavigationCommand
}

class ReceiptDetailsNavigationImpl : ReceiptDetailsNavigation {

  override fun navigateToReceiptScan(): NavigationCommand {
    val action = ReceiptDetailsBottomDialogDirections.receiptDetailsToScanner(ScanMode.RECEIPT)
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToMachineScan(): NavigationCommand {
    val action = ReceiptDetailsBottomDialogDirections.receiptDetailsToScanner(ScanMode.MACHINE)
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToMachineEnter(receiptId: Int): NavigationCommand {
    val action = ReceiptDetailsBottomDialogDirections.receiptDetailsToMachineSelect(receiptId)
    return NavigationCommand.ByAction(action)
  }

}
