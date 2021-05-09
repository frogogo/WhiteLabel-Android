package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.coupon.Coupon
import ru.frogogo.whitelabel.data.model.api.coupon.CouponData
import ru.frogogo.whitelabel.data.model.api.coupon.CouponImage
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponImageUiModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel

fun Coupon.toDomain(data: CouponData): CouponUiModel = CouponUiModel(
  id = id,
  name = data.name,
  steps = data.steps,
  image = CouponImageUiModel(largeUrl = "", thumbUrl = ""),
  code = code
)

fun List<Coupon>.toDomain(data: CouponData): List<CouponUiModel> =
  map { it.toDomain(data) }

fun CouponImage.toDomain(): CouponImageUiModel = CouponImageUiModel(
  largeUrl = large,
  thumbUrl = thumb,
)
