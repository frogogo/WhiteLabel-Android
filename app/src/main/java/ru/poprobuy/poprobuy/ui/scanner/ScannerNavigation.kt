package ru.poprobuy.poprobuy.ui.scanner

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface ScannerNavigation {
  fun navigateToHelp(): NavigationCommand
  fun navigateToManualMachineEnter(): NavigationCommand
}

class ScannerNavigationImpl : ScannerNavigation {

  override fun navigateToHelp(): NavigationCommand {
    return NavigationCommand.ById(R.id.scanner_to_products)
  }

  override fun navigateToManualMachineEnter(): NavigationCommand {
    return NavigationCommand.Back // TODO: 04.07.2020 Real action
  }

}
