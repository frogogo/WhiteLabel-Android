package ru.frogogo.whitelabel.data.model.api.home

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeProgress(
  @Json(name = "current")
  val current: Int,
  @Json(name = "target")
  val target: Int,
)
