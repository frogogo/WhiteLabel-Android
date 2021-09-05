package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.Item
import ru.frogogo.whitelabel.data.model.ui.ItemUiModel

fun Item.toDomain(): ItemUiModel =
  ItemUiModel(
    id = id,
    name = name,
    imageUrl = imageUrl,
    price = price,
    discountedPrice = discountedPrice,
    specs = specs,
  )
