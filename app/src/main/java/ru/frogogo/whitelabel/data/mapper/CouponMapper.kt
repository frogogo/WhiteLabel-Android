package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.coupon.Coupon
import ru.frogogo.whitelabel.data.model.api.coupon.CouponCode
import ru.frogogo.whitelabel.data.model.api.coupon.CouponImage
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponCodeUiModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponImageUiModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel

fun Coupon.toDomain(): CouponUiModel = CouponUiModel(
  id = id,
  name = name,
  description = description,
  image = image.toDomain(),
  code = code.toDomain(),
)

fun CouponImage.toDomain(): CouponImageUiModel = CouponImageUiModel(
  largeUrl = large,
  thumbUrl = thumb,
)

fun CouponCode.toDomain(): CouponCodeUiModel = CouponCodeUiModel(
  value = value,
  type = type,
)
