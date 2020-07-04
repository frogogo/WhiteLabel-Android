package ru.poprobuy.poprobuy.ui.scanner

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface ScannerNavigation {
  fun navigateBack(): NavigationCommand
  fun navigateToHelp(): NavigationCommand
  fun navigateToManualMachineEnter(): NavigationCommand
}

class ScannerNavigationImpl : ScannerNavigation {

  override fun navigateBack(): NavigationCommand {
    return NavigationCommand.Back
  }

  override fun navigateToHelp(): NavigationCommand {
    return NavigationCommand.Back // TODO: 04.07.2020 Real action
  }

  override fun navigateToManualMachineEnter(): NavigationCommand {
    return NavigationCommand.Back // TODO: 04.07.2020 Real action
  }

}
