package ru.frogogo.whitelabel.data.model.api.home

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.frogogo.whitelabel.data.model.api.Photo

@JsonClass(generateAdapter = true)
data class HomePromotion(
  @Json(name = "name")
  val name: String,
  @Json(name = "photo")
  val photo: Photo,
  @Json(name = "steps")
  val steps: List<String>,
)
