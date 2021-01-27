package ru.poprobuy.poprobuy.ui.home

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.dictionary.ScanMode
import ru.poprobuy.poprobuy.extension.toCommand

interface HomeNavigation {
  fun navigateToProfile(): NavigationCommand
  fun navigateToReceiptScan(): NavigationCommand
  fun navigateToMachineScan(receiptId: Int): NavigationCommand
  fun navigateToMachineEnter(receiptId: Int): NavigationCommand
}

class HomeNavigationImpl : HomeNavigation {

  override fun navigateToProfile(): NavigationCommand =
    HomeFragmentDirections.homeToProfile().toCommand()

  override fun navigateToReceiptScan(): NavigationCommand =
    HomeFragmentDirections.homeToScanner(ScanMode.RECEIPT).toCommand()

  override fun navigateToMachineScan(receiptId: Int): NavigationCommand =
    HomeFragmentDirections.homeToScanner(
      mode = ScanMode.MACHINE,
      receiptId = receiptId
    ).toCommand()

  override fun navigateToMachineEnter(receiptId: Int): NavigationCommand =
    HomeFragmentDirections.homeToMachineSelect(receiptId).toCommand()

}
