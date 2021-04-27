package ru.frogogo.whitelabel.ui.products

import ru.frogogo.whitelabel.data.model.ui.machine.VendingCellUiModel

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
