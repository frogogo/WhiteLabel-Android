package ru.frogogo.whitelabel.ui.products.select

sealed class MachineProductSelectionCommand {
  data class SetCancelable(val isCancelable: Boolean) : MachineProductSelectionCommand()
  object DismissDialog : MachineProductSelectionCommand()
}
