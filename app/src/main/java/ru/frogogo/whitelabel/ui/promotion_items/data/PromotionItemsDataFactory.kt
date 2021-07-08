package ru.frogogo.whitelabel.ui.promotion_items.data

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.ItemUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomePromotionUiModel

interface PromotionItemsDataFactory {

  fun create(promotion: HomePromotionUiModel, items: List<ItemUiModel>): List<RecyclerViewItem>
}
