package ru.poprobuy.poprobuy.data.model.ui.product

import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem

private const val ID = "ITEM_PRODUCT"

data class ProductUiModel(
  val id: Int,
  val name: String,
  val imageUrl: String,
  val inStock: Boolean,
  val triedBefore: Boolean
) : RecyclerViewItem {

  override fun getId(): Any = "$ID$id"

  /**
   * @return true if current product is active and available
   */
  fun isActive(): Boolean = inStock && !triedBefore

}
