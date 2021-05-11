package ru.frogogo.whitelabel.data.model.ui.coupon

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.PhotoUiModel

data class CouponUiModel(
  val id: Int,
  val name: String,
  val steps: List<String>,
  val photo: PhotoUiModel,
  val code: String,
) : RecyclerViewItem {

  override fun getId(): Any = "ITEM_COUPON$id"
}
