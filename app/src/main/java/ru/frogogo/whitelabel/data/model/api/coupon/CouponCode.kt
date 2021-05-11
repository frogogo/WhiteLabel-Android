package ru.frogogo.whitelabel.data.model.api.coupon

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.frogogo.whitelabel.dictionary.CouponCodeType

@JsonClass(generateAdapter = true)
data class CouponCode(
  @Json(name = "value")
  val value: String,
  @Json(name = "type")
  val type: CouponCodeType,
)
