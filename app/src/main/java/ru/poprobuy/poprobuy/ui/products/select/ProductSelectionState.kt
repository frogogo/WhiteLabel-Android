package ru.poprobuy.poprobuy.ui.products.select

import ru.poprobuy.poprobuy.data.model.ui.product.ProductUiModel

sealed class ProductSelectionState {
  data class Product(val product: ProductUiModel, val isLoading: Boolean = false) : ProductSelectionState()
  object Success : ProductSelectionState()
  object Error : ProductSelectionState()
}
