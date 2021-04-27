package ru.frogogo.whitelabel.data.model.api.receipt

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReceiptDistributionNetwork(
  @Json(name = "name")
  val name: String,
)
