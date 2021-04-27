package ru.frogogo.whitelabel.ui.machine_select

import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.data.model.ui.machine.VendingMachineUiModel

interface MachineSelectNavigation {
  fun navigateToProducts(receiptId: Int, vendingMachine: VendingMachineUiModel): NavigationCommand
}

class MachineSelectNavigationImpl : MachineSelectNavigation {

  override fun navigateToProducts(receiptId: Int, vendingMachine: VendingMachineUiModel): NavigationCommand =
    MachineSelectFragmentDirections.machineSelectToProducts(receiptId, vendingMachine).toCommand()
}
