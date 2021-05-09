package ru.frogogo.whitelabel.data.model.api.coupon

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CouponData(
  @Json(name = "name")
  val name: String,
  @Json(name = "photo_url")
  val photoUrl: String,
  @Json(name = "steps")
  val steps: List<String>,
)
