package ru.frogogo.whitelabel.data.model.ui.item

data class ItemInfoUiModel(
  val id: Int,
  val name: String,
  val imageUrl: String,
  val price: Int,
  val discountedPrice: Int,
  val specs: String?,
  val description: String,
)
