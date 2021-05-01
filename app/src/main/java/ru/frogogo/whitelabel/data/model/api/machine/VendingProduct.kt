package ru.frogogo.whitelabel.data.model.api.machine

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.frogogo.whitelabel.dictionary.VendingProductState

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
