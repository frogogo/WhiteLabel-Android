package ru.poprobuy.poprobuy.ui.machine_select

sealed class MachineSelectCommand {
  object HideKeyboard : MachineSelectCommand()
}
