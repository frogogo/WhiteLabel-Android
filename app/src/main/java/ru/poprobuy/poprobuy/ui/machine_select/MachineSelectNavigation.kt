package ru.poprobuy.poprobuy.ui.machine_select

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface MachineSelectNavigation {
  fun navigateToProducts(): NavigationCommand
}

class MachineSelectNavigationImpl : MachineSelectNavigation {

  override fun navigateToProducts(): NavigationCommand {
    return NavigationCommand.ById(R.id.machine_select_to_products)
  }

}
