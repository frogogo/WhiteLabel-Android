package ru.frogogo.whitelabel.ui.home

import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.extension.toCommand

interface HomeNavigation {
  fun navigateToProfile(): NavigationCommand
  fun navigateToReceiptScan(): NavigationCommand
}

class HomeNavigationImpl : HomeNavigation {

  override fun navigateToProfile(): NavigationCommand =
    HomeFragmentDirections.homeToProfile().toCommand()

  override fun navigateToReceiptScan(): NavigationCommand =
    HomeFragmentDirections.homeToScanner().toCommand()
}
