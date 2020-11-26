package ru.poprobuy.poprobuy.data.model.api.machine

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VendingMachine(
  @Json(name = "public_id")
  val id: Int,
  @Json(name = "address")
  val address: String,
  @Json(name = "vending_cells")
  val vendingCells: List<VendingCell>,
  @Json(name = "vending_cells_columns")
  val vendingCellsColumns: Int,
  @Json(name = "vending_cells_rows")
  val vendingCellsRows: Int,
)
