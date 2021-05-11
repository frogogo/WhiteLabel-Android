package ru.frogogo.whitelabel.data.model.ui.home

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem

data class HomeProgressUiModel(
  val current: Int,
  val target: Int,
) : RecyclerViewItem {

  override fun getId(): Any = "item_coupon_progress"
}
