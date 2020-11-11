package ru.poprobuy.poprobuy.ui.profile.receipts.details

import com.github.ajalt.timberkt.d
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel

class ReceiptDetailsViewModel(
  private val navigation: ReceiptDetailsNavigation,
) : BaseViewModel() {

  fun navigateToReceiptScan() {
    d { "Navigating to receipt scan" }
    navigation.navigateToReceiptScan().navigate()
  }

  fun navigateToMachineEnter() {
    d { "Navigating to machine enter" }
    navigation.navigateToMachineEnter().navigate()
  }

  fun navigateToMachineScan() {
    d { "Navigating to machine scan" }
    navigation.navigateToMachineScan().navigate()
  }

}
