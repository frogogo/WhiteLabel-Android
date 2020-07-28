package ru.poprobuy.poprobuy.ui.products.select

import ru.poprobuy.poprobuy.data.model.ui.product.ProductUiModel

sealed class ProductSelectionCommand {
  data class SetProduct(val product: ProductUiModel) : ProductSelectionCommand()
}
