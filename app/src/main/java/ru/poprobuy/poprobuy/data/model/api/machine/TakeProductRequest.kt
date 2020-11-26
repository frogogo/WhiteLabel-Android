package ru.poprobuy.poprobuy.data.model.api.machine

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TakeProductRequest(
  @Json(name = "item_id")
  val itemId: Int,
  @Json(name = "receipt_id")
  val receiptId: Int,
  @Json(name = "vending_cell_id")
  val vendingCellId: Int,
)
