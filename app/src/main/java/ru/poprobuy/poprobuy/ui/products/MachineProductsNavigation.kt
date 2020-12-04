package ru.poprobuy.poprobuy.ui.products

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand

interface MachineProductsNavigation {
  fun navigateToHome(): NavigationCommand
}

class MachineProductsNavigationImpl : MachineProductsNavigation {

  override fun navigateToHome(): NavigationCommand {
    val action = MachineProductsFragmentDirections.productsToHome()
    return NavigationCommand.ByAction(action)
  }

}
