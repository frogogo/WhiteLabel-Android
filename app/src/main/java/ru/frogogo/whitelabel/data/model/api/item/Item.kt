package ru.frogogo.whitelabel.data.model.api.item

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
  @Json(name = "id")
  val id: Int,
  @Json(name = "name")
  val name: String,
  @Json(name = "image_url")
  val imageUrl: String,
  @Json(name = "price")
  val price: Int,
  @Json(name = "discounted_price")
  val discountedPrice: Int,
  @Json(name = "specs")
  val specs: String?,
)
