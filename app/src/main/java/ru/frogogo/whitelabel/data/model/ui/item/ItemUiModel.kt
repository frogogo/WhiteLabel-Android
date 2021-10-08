package ru.frogogo.whitelabel.data.model.ui.item

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import java.math.BigDecimal

data class ItemUiModel(
  val id: Int,
  val name: String,
  val imageUrl: String,
  val price: BigDecimal,
  val discountedPrice: BigDecimal,
  val specs: String?,
) : RecyclerViewItem {

  override fun getId(): Any = "item_item$id"
}
