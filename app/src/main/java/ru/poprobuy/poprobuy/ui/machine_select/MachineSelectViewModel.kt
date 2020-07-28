package ru.poprobuy.poprobuy.ui.machine_select

import com.github.ajalt.timberkt.d
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel

class MachineSelectViewModel(
  private val navigation: MachineSelectNavigation
) : BaseViewModel() {

  fun selectMachine(machineNumber: String) {
    d { "Selecting machine - $machineNumber" }
    hideKeyboard()
    navigation.navigateToProducts().navigate()
  }

}
