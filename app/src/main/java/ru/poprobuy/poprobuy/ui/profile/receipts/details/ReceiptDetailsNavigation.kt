package ru.poprobuy.poprobuy.ui.profile.receipts.details

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.dictionary.ScanMode

interface ReceiptDetailsNavigation {
  fun navigateToReceiptScan(): NavigationCommand
  fun navigateToMachineScan(receiptId: Int): NavigationCommand
  fun navigateToMachineEnter(receiptId: Int): NavigationCommand
}

class ReceiptDetailsNavigationImpl : ReceiptDetailsNavigation {

  override fun navigateToReceiptScan(): NavigationCommand {
    val action = ReceiptDetailsBottomDialogDirections.receiptDetailsToScanner(mode = ScanMode.RECEIPT)
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToMachineScan(receiptId: Int): NavigationCommand {
    val action = ReceiptDetailsBottomDialogDirections.receiptDetailsToScanner(
      mode = ScanMode.MACHINE,
      receiptId = receiptId
    )
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToMachineEnter(receiptId: Int): NavigationCommand {
    val action = ReceiptDetailsBottomDialogDirections.receiptDetailsToMachineSelect(receiptId)
    return NavigationCommand.ByAction(action)
  }

}
