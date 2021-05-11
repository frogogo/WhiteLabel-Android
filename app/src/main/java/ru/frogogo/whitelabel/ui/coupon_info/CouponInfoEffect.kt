package ru.frogogo.whitelabel.ui.coupon_info

import ru.frogogo.whitelabel.data.model.ui.coupon.CouponCodeUiModel

sealed class CouponInfoEffect {

  data class ShowCode(
    val code: CouponCodeUiModel,
  ) : CouponInfoEffect()
}
