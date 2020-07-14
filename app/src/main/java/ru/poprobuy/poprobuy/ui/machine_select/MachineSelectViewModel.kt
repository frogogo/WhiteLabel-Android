package ru.poprobuy.poprobuy.ui.machine_select

import androidx.lifecycle.LiveData
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel

class MachineSelectViewModel(
  private val navigation: MachineSelectNavigation
) : BaseViewModel() {

  private val _command = LiveEvent<MachineSelectCommand>()
  val command: LiveData<MachineSelectCommand> get() = _command

  fun selectMachine(machineNumber: String) {
    d { "Selecting machine - $machineNumber" }
    _command.postValue(MachineSelectCommand.HideKeyboard)
    navigation.navigateToProducts().navigate()
  }

}
