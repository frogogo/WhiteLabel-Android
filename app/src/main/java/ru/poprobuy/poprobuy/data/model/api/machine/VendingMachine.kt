package ru.poprobuy.poprobuy.data.model.api.machine

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VendingMachine(
  @Json(name = "public_id")
  val id: Int,
  @Json(name = "vending_cells")
  val vendingCells: List<VendingCell>,
  @Json(name = "assign_expires_in")
  val assignExpiresIn: Int,
)
