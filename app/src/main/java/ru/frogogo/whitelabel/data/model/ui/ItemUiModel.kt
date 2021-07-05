package ru.frogogo.whitelabel.data.model.ui

data class ItemUiModel(
  val id: Int,
  val name: String,
  val imageUrl: String,
  val price: Int,
  val discountedPrice: Int,
  val specs: String,
)
