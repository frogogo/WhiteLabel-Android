package ru.poprobuy.poprobuy.ui.home

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.dictionary.ScanMode

interface HomeNavigation {
  fun navigateToProfile(): NavigationCommand
  fun navigateToReceiptScan(): NavigationCommand
  fun navigateToMachineScan(): NavigationCommand
  fun navigateToMachineEnter(): NavigationCommand
}

class HomeNavigationImpl : HomeNavigation {

  override fun navigateToProfile(): NavigationCommand {
    return NavigationCommand.ById(R.id.home_to_profile)
  }

  override fun navigateToReceiptScan(): NavigationCommand {
    val action = HomeFragmentDirections.homeToScanner(ScanMode.RECEIPT)
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToMachineScan(): NavigationCommand {
    // TODO: 03.07.2020 Add scan type machine
    val action = HomeFragmentDirections.homeToScanner(ScanMode.MACHINE)
    return NavigationCommand.ByAction(action)
  }

  override fun navigateToMachineEnter(): NavigationCommand {
    // TODO: 02.07.2020
    return NavigationCommand.Back
  }

}
