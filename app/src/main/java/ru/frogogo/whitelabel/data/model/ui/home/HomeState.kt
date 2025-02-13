package ru.frogogo.whitelabel.data.model.ui.home

import ru.frogogo.whitelabel.data.model.ui.item.ItemUiModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel

sealed class HomeState {

  val items = mutableListOf<ItemUiModel>()

  data class Empty(
    val promotion: HomePromotionUiModel,
  ) : HomeState()

  data class Progress(
    val promotion: HomePromotionUiModel,
    val progress: HomeProgressUiModel,
    val coupons: List<CouponUiModel>,
    val scanAvailable: Boolean,
  ) : HomeState()
}

fun HomeState.isScanAvailable(): Boolean =
  this is HomeState.Empty || (this is HomeState.Progress && this.scanAvailable)
