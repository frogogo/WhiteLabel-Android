package ru.frogogo.whitelabel.data.model.ui.coupon

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem

data class CouponUiModel(
  val id: Int,
  val name: String,
  val description: String,
  val image: CouponImageUiModel,
  val qrString: String,
) : RecyclerViewItem {

  override fun getId(): Any = "ITEM_COUPON$id"
}
