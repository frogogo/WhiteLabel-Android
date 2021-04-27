package ru.frogogo.whitelabel.data.model.api.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfirmationCodeRequestResponse(
  @Json(name = "password_refresh_rate")
  val passwordRefreshRate: Int,
)
