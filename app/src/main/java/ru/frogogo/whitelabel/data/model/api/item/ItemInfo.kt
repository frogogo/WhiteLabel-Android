package ru.frogogo.whitelabel.data.model.api.item

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class ItemInfo(
  @Json(name = "id")
  val id: Int,
  @Json(name = "name")
  val name: String,
  @Json(name = "image_url")
  val imageUrl: String,
  @Json(name = "price")
  val price: BigDecimal,
  @Json(name = "discounted_price")
  val discountedPrice: BigDecimal,
  @Json(name = "specs")
  val specs: String?,
  @Json(name = "description")
  val description: String,
)
