package ru.frogogo.whitelabel.data.model.api.machine

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VendingCell(
  @Json(name = "id")
  val id: Int,
  @Json(name = "item")
  val item: VendingProduct?,
)
