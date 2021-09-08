package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.item.Item
import ru.frogogo.whitelabel.data.model.api.item.ItemInfo
import ru.frogogo.whitelabel.data.model.ui.item.ItemInfoUiModel
import ru.frogogo.whitelabel.data.model.ui.item.ItemUiModel

fun Item.toDomain(): ItemUiModel =
  ItemUiModel(
    id = id,
    name = name,
    imageUrl = imageUrl,
    price = price,
    discountedPrice = discountedPrice,
    specs = specs,
  )

fun ItemInfo.toDomain(): ItemInfoUiModel =
  ItemInfoUiModel(
    id = id,
    name = name,
    imageUrl = imageUrl,
    price = price,
    discountedPrice = discountedPrice,
    specs = specs,
    description = description,
  )
