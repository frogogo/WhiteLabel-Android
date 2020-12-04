package ru.poprobuy.poprobuy.ui.scanner

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.util.Constants

interface ScannerNavigation {
  fun navigateToMachineHelp(): NavigationCommand
  fun navigateToReceiptHelp(): NavigationCommand
  fun navigateToManualMachineEnter(receiptId: Int): NavigationCommand
  fun navigateToHome(): NavigationCommand
  fun navigateToProducts(receiptId: Int, vendingMachine: VendingMachineUiModel): NavigationCommand
}

class ScannerNavigationImpl : ScannerNavigation {

  override fun navigateToMachineHelp(): NavigationCommand {
    return NavigationCommand.ByWebUrl(Constants.HELP_SCAN_MACHINE_URL, R.string.scanner_help_machine_title)
  }

  override fun navigateToReceiptHelp(): NavigationCommand {
    return NavigationCommand.ByWebUrl(Constants.HELP_SCAN_RECEIPT_URL, R.string.scanner_help_receipt_title)
  }

  override fun navigateToManualMachineEnter(receiptId: Int): NavigationCommand {
    val action = ScannerFragmentDirections.scannerToMachineSelect(receiptId)
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToHome(): NavigationCommand {
    val action = ScannerFragmentDirections.scannerToHome()
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToProducts(receiptId: Int, vendingMachine: VendingMachineUiModel): NavigationCommand {
    val action = ScannerFragmentDirections.scannerToProducts(receiptId, vendingMachine)
    return NavigationCommand.ByAction(action)
  }

}
