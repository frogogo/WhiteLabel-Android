package ru.frogogo.whitelabel.data.model.api.coupon

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coupon(
  @Json(name = "id")
  val id: Int,
  @Json(name = "name")
  val name: String,
  @Json(name = "description")
  val description: String,
  @Json(name = "image")
  val image: CouponImage,
  @Json(name = "qr_string")
  val qrString: String,
)
