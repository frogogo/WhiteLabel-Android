package ru.poprobuy.poprobuy.ui.scanner

import com.github.ajalt.timberkt.d
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel

class ScannerViewModel(
  private val navigation: ScannerNavigation
) : BaseViewModel() {

  fun handleQrString(string: String) {
    d { "Handling QR string - $string" }
  }

  fun navigateToHelp() {
    d { "Navigating to help" }
    navigation.navigateToHelp().navigate()
  }

  fun navigateToManualMachineEnter() {
    d { "Navigating to manual machine enter" }
    navigation.navigateToManualMachineEnter().navigate()
  }

}
