package ru.frogogo.whitelabel.ui.item_info.data

import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.item.ItemUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomePromotionUiModel
import ru.frogogo.whitelabel.ui.home.model.HomeEmptyState
import ru.frogogo.whitelabel.ui.home.model.HomeSectionHeader
import ru.frogogo.whitelabel.ui.item_info.model.PromotionItemsRule

class ItemInfoDataFactoryImpl : ItemInfoDataFactory {

  override fun create(promotion: HomePromotionUiModel, items: List<ItemUiModel>): List<RecyclerViewItem> {
    val list = mutableListOf<RecyclerViewItem>()

    list += HomeEmptyState(promotion)
    list += PromotionItemsRule

    if (items.isNotEmpty()) {
      list += HomeSectionHeader(R.string.home_empty_items)
      list += items
    }

    return list
  }
}
