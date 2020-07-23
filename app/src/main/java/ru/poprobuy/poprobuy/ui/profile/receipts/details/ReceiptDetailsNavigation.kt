package ru.poprobuy.poprobuy.ui.profile.receipts.details

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.dictionary.ScanMode

interface ReceiptDetailsNavigation {
  fun navigateToReceiptScan(): NavigationCommand
  fun navigateToMachineScan(): NavigationCommand
  fun navigateToMachineEnter(): NavigationCommand
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

  override fun navigateToMachineEnter(): NavigationCommand {
    return NavigationCommand.ById(R.id.receipt_details_to_machine_select)
  }

}
