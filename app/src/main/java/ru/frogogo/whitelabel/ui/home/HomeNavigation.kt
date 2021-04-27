package ru.frogogo.whitelabel.ui.home

import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.dictionary.ScanMode

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
