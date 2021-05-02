package ru.frogogo.whitelabel.data.model.ui.home

import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel

sealed class HomeState {

  object Empty : HomeState()

  data class Progress(
    val couponProgress: HomeCouponProgressUiModel,
    val coupons: List<CouponUiModel>,
  ) : HomeState()
}
