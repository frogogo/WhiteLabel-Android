package ru.poprobuy.poprobuy.ui.machine_select

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel

interface MachineSelectNavigation {
  fun navigateToProducts(receiptId: Int, vendingMachine: VendingMachineUiModel): NavigationCommand
}

class MachineSelectNavigationImpl : MachineSelectNavigation {

  override fun navigateToProducts(receiptId: Int, vendingMachine: VendingMachineUiModel): NavigationCommand {
    val action = MachineSelectFragmentDirections.machineSelectToProducts(receiptId, vendingMachine)
    return NavigationCommand.ByAction(action)
  }

}
