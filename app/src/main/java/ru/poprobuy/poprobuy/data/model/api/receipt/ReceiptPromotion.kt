package ru.poprobuy.poprobuy.data.model.api.receipt

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReceiptPromotion(
  @Json(name = "name")
  val name: String,
)
