package ru.poprobuy.poprobuy.ui.products

import ru.poprobuy.poprobuy.core.navigation.NavigationCommand
import ru.poprobuy.poprobuy.extension.toCommand

interface MachineProductsNavigation {
  fun navigateToHome(): NavigationCommand
}

class MachineProductsNavigationImpl : MachineProductsNavigation {

  override fun navigateToHome(): NavigationCommand =
    MachineProductsFragmentDirections.productsToHome().toCommand()

}
