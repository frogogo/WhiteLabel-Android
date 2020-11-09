package ru.poprobuy.poprobuy.data.model.api.receipt

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.poprobuy.poprobuy.dictionary.ReceiptState
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
  val timestamp: Date
)
