package ru.frogogo.whitelabel.data.model.api.home

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class HomeProgress(
  @Json(name = "current")
  val current: BigDecimal,
  @Json(name = "target")
  val target: BigDecimal,
)
