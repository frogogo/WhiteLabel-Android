package ru.poprobuy.poprobuy.data.model.api.receipt

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReceiptRejectReason(
  @Json(name = "reason")
  val reason: String,
  @Json(name = "reason_text")
  val reasonText: String,
)
