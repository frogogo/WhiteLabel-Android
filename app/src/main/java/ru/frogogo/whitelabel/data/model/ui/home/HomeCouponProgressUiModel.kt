package ru.frogogo.whitelabel.data.model.ui.home

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem

data class HomeCouponProgressUiModel(
  val progressCurrent: Int,
  val progressTarget: Int,
  val notice: String,
) : RecyclerViewItem {

  override fun getId(): Any = "item_coupon_progress"

}
