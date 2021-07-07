package ru.frogogo.whitelabel.ui.home.model

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.home.HomePromotionUiModel

data class HomeItemsButton(
  val promotion: HomePromotionUiModel,
) : RecyclerViewItem {

  override fun getId(): Any = "ITEM_ITEMS_BUTTON"
}
