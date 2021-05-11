package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.coupon.Coupon
import ru.frogogo.whitelabel.data.model.api.coupon.CouponData
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponCodeUiModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.dictionary.CouponCodeType

fun Coupon.toDomain(data: CouponData): CouponUiModel = CouponUiModel(
  id = id,
  name = data.name,
  steps = data.steps,
  photo = data.photo.toDomain(),
  code = CouponCodeUiModel(code, CouponCodeType.CODE_128)
)

fun List<Coupon>.toDomain(data: CouponData): List<CouponUiModel> =
  map { it.toDomain(data) }
