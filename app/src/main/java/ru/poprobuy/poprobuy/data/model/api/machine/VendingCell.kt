package ru.poprobuy.poprobuy.data.model.api.machine

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VendingCell(
  @Json(name = "row")
  val row: Int,
  @Json(name = "column")
  val column: Int,
  @Json(name = "item")
  val item: VendingProduct?,
  @Json(name = "quantity")
  val quantity: Int,
)
