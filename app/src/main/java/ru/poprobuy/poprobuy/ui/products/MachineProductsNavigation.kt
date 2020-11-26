package ru.poprobuy.poprobuy.ui.products

import ru.poprobuy.poprobuy.arch.navigation.NavigationCommand

interface MachineProductsNavigation {
  fun navigateToHome(): NavigationCommand
}

class MachineProductsNavigationImpl : MachineProductsNavigation {

  override fun navigateToHome(): NavigationCommand {
    val action = MachineProductsFragmentDirections.productsToHome()
    return NavigationCommand.ByAction(action)
  }

}
