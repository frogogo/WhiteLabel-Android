package ru.frogogo.whitelabel.ui.item_info.data

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.item.ItemUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomePromotionUiModel

interface ItemInfoDataFactory {

  fun create(promotion: HomePromotionUiModel, items: List<ItemUiModel>): List<RecyclerViewItem>
}
