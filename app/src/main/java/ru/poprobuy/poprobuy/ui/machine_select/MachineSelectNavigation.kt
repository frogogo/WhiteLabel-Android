package ru.poprobuy.poprobuy.ui.machine_select

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.extension.toCommand

interface MachineSelectNavigation {
  fun navigateToProducts(receiptId: Int, vendingMachine: VendingMachineUiModel): NavigationCommand
}

class MachineSelectNavigationImpl : MachineSelectNavigation {

  override fun navigateToProducts(receiptId: Int, vendingMachine: VendingMachineUiModel): NavigationCommand =
    MachineSelectFragmentDirections.machineSelectToProducts(receiptId, vendingMachine).toCommand()
}
