package ru.poprobuy.poprobuy.data.model.api.machine

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.poprobuy.poprobuy.dictionary.VendingProductState

@JsonClass(generateAdapter = true)
data class VendingProduct(
  @Json(name = "id")
  val id: Int,
  @Json(name = "name")
  val name: String,
  @Json(name = "image_url")
  val imageUrl: String,
  @Json(name = "state")
  val state: VendingProductState,
)
