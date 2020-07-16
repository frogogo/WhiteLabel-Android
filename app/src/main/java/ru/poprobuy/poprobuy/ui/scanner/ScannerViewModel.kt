package ru.poprobuy.poprobuy.ui.scanner

import com.github.ajalt.timberkt.d
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.dictionary.ScanMode

class ScannerViewModel(
  private val scanMode: ScanMode,
  private val navigation: ScannerNavigation
) : BaseViewModel() {

  fun handleQrString(string: String) {
    d { "Handling QR string - $string" }
  }

  fun navigateToHelp() {
    d { "Navigating to help for scan mode - $scanMode" }
    when (scanMode) {
      ScanMode.RECEIPT -> navigation.navigateToReceiptHelp()
      ScanMode.MACHINE -> navigation.navigateToMachineHelp()
    }.navigate()
  }

  fun navigateToManualMachineEnter() {
    d { "Navigating to manual machine enter" }
    navigation.navigateToManualMachineEnter().navigate()
  }

}
