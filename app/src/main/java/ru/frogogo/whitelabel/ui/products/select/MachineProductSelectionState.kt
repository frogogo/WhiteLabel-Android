package ru.frogogo.whitelabel.ui.products.select

import ru.frogogo.whitelabel.data.model.ui.machine.VendingProductUiModel

sealed class MachineProductSelectionState {

  data class Product(
    val product: VendingProductUiModel,
    val isLoading: Boolean = false,
  ) : MachineProductSelectionState()

  object Success : MachineProductSelectionState()
}
