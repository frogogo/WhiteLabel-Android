package ru.frogogo.whitelabel.data.model.api.coupon

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CouponImage(

  @Json(name = "thumb")
  val thumb: String,

  @Json(name = "large")
  val large: String,
)
