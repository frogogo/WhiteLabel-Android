package ru.frogogo.whitelabel.data.model.api.machine

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VendingMachineAssignRequest(
  @Json(name = "receipt_id")
  val receiptId: Int,
)
