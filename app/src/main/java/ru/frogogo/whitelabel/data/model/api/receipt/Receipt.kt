package ru.frogogo.whitelabel.data.model.api.receipt

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.frogogo.whitelabel.dictionary.ReceiptState
import java.math.BigDecimal
import java.util.Date

@JsonClass(generateAdapter = true)
data class Receipt(
  @Json(name = "id")
  val id: Int,
  @Json(name = "number")
  val number: Int,
  @Json(name = "sum")
  val sum: BigDecimal,
  @Json(name = "state")
  val state: ReceiptState,
  @Json(name = "timestamp")
  val timestamp: Date,
  @Json(name = "reject_reason")
  val rejectReason: ReceiptRejectReason?,
)
