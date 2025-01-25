package ru.frogogo.whitelabel.data.model.ui.item

import java.math.BigDecimal

data class ItemInfoUiModel(
  val id: Int,
  val name: String,
  val imageUrl: String,
  val price: BigDecimal,
  val discountedPrice: BigDecimal,
  val specs: String?,
  val description: String,
)
