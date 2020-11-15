package ru.poprobuy.poprobuy.ui.home

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.dictionary.ScanMode

interface HomeNavigation {
  fun navigateToProfile(): NavigationCommand
  fun navigateToReceiptScan(): NavigationCommand
  fun navigateToMachineScan(receiptId: Int): NavigationCommand
  fun navigateToMachineEnter(receiptId: Int): NavigationCommand
}

class HomeNavigationImpl : HomeNavigation {

  override fun navigateToProfile(): NavigationCommand {
    val action = HomeFragmentDirections.homeToProfile()
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToReceiptScan(): NavigationCommand {
    val action = HomeFragmentDirections.homeToScanner(ScanMode.RECEIPT)
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToMachineScan(receiptId: Int): NavigationCommand {
    val action = HomeFragmentDirections.homeToScanner(
      mode = ScanMode.MACHINE,
      receiptId = receiptId
    )
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToMachineEnter(receiptId: Int): NavigationCommand {
    val action = HomeFragmentDirections.homeToMachineSelect(receiptId)
    return NavigationCommand.ByAction(action)
  }

}
