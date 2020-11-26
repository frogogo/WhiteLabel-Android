package ru.poprobuy.poprobuy.ui.products.select

sealed class MachineProductSelectionCommand {
  data class SetCancelable(val isCancelable: Boolean) : MachineProductSelectionCommand()
  object DismissDialog : MachineProductSelectionCommand()
}
