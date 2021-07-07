package ru.frogogo.whitelabel.data.model.ui.home

import ru.frogogo.whitelabel.data.model.ui.PhotoUiModel

data class HomePromotionUiModel(
  val name: String,
  val photo: PhotoUiModel,
  val steps: List<String>,
  val price: Int,
  val priceDiscounted: Int,
)
