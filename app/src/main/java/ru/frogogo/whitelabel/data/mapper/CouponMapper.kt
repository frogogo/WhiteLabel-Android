package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.coupon.CouponCode
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponCodeUiModel

fun CouponCode.toDomain(): CouponCodeUiModel =
  CouponCodeUiModel(
    value = value,
    type = type,
  )
