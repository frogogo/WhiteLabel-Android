package ru.frogogo.whitelabel.data.model.ui

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem

data class ItemUiModel(
  val id: Int,
  val name: String,
  val imageUrl: String,
  val price: Int,
  val discountedPrice: Int,
  val specs: String,
) : RecyclerViewItem {

  override fun getId(): Any = "item_item$id"
}
