package ru.frogogo.whitelabel.ui.home

import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel

sealed class HomeEffect {

  object ShowLoadingError : HomeEffect()

  data class ShowCouponReceived(
    val coupon: CouponUiModel,
  ) : HomeEffect()
}
