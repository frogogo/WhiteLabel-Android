package ru.poprobuy.poprobuy.ui.products.select

import ru.poprobuy.poprobuy.data.model.ui.machine.VendingProductUiModel

sealed class MachineProductSelectionState {

  data class Product(
    val product: VendingProductUiModel,
    val isLoading: Boolean = false,
  ) : MachineProductSelectionState()

  object Success : MachineProductSelectionState()

}
