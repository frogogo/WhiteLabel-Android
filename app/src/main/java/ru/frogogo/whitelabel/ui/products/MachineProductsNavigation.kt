package ru.frogogo.whitelabel.ui.products

import ru.frogogo.whitelabel.core.navigation.NavigationCommand

interface MachineProductsNavigation {
  fun navigateToHome(): NavigationCommand
}

class MachineProductsNavigationImpl : MachineProductsNavigation {

  override fun navigateToHome(): NavigationCommand =
    MachineProductsFragmentDirections.productsToHome().toCommand()
}
