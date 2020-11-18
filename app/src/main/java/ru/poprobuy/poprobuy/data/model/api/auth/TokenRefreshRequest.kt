package ru.poprobuy.poprobuy.data.model.api.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class TokenRefreshRequest(
  @Json(name = "refresh_token")
  val refreshToken: String,
)
