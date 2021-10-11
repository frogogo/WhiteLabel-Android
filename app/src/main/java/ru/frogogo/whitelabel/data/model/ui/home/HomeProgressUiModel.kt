package ru.frogogo.whitelabel.data.model.ui.home

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import java.math.BigDecimal

data class HomeProgressUiModel(
  val current: BigDecimal,
  val target: BigDecimal,
) : RecyclerViewItem {

  override fun getId(): Any = "item_coupon_progress"
}
