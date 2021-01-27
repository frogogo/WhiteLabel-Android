package ru.poprobuy.poprobuy.ui.scanner

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.extension.toCommand
import ru.poprobuy.poprobuy.util.Constants

interface ScannerNavigation {
  fun navigateToMachineHelp(): NavigationCommand
  fun navigateToReceiptHelp(): NavigationCommand
  fun navigateToManualMachineEnter(receiptId: Int): NavigationCommand
  fun navigateToHome(): NavigationCommand
  fun navigateToProducts(receiptId: Int, vendingMachine: VendingMachineUiModel): NavigationCommand
}

class ScannerNavigationImpl : ScannerNavigation {

  override fun navigateToMachineHelp(): NavigationCommand =
    NavigationCommand.ByWebUrl(
      url = Constants.HELP_SCAN_MACHINE_URL,
      titleRes = R.string.scanner_help_machine_title
    )

  override fun navigateToReceiptHelp(): NavigationCommand =
    NavigationCommand.ByWebUrl(
      url = Constants.HELP_SCAN_RECEIPT_URL,
      titleRes = R.string.scanner_help_receipt_title
    )

  override fun navigateToManualMachineEnter(receiptId: Int): NavigationCommand =
    ScannerFragmentDirections.scannerToMachineSelect(receiptId).toCommand()

  override fun navigateToHome(): NavigationCommand =
    ScannerFragmentDirections.scannerToHome().toCommand()

  override fun navigateToProducts(receiptId: Int, vendingMachine: VendingMachineUiModel): NavigationCommand =
    ScannerFragmentDirections.scannerToProducts(receiptId, vendingMachine).toCommand()

}
