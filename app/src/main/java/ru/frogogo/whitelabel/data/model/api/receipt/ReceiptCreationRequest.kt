package ru.frogogo.whitelabel.data.model.api.receipt

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReceiptCreationRequest(
  @Json(name = "qr_string")
  val qrString: String,
)
