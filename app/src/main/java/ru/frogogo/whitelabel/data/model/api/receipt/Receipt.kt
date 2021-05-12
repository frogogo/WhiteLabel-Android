package ru.frogogo.whitelabel.data.model.api.receipt

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.frogogo.whitelabel.dictionary.ReceiptState
import java.util.*

@JsonClass(generateAdapter = true)
data class Receipt(
  @Json(name = "id")
  val id: Int,
  @Json(name = "number")
  val number: Int,
  @Json(name = "sum")
  val sum: Int,
  @Json(name = "state")
  val state: ReceiptState,
  @Json(name = "timestamp")
  val timestamp: Date,
  @Json(name = "reject_reason")
  val rejectReason: ReceiptRejectReason?,
)
