package ru.poprobuy.poprobuy.ui.products

import ru.poprobuy.poprobuy.data.model.ui.machine.VendingCellUiModel

sealed class MachineProductsCommand {

  data class ShowSelectionDialog(
    val receiptId: Int,
    val vendingMachineId: Int,
    val vendingCell: VendingCellUiModel,
  ) : MachineProductsCommand()

  data class ShowErrorDialog(
    val error: String,
  ) : MachineProductsCommand()
}
