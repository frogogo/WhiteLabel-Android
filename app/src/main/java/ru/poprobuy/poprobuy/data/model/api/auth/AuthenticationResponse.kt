package ru.poprobuy.poprobuy.data.model.api.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticationResponse(
  @Json(name = "jwt")
  val accessToken: String,
  @Json(name = "is_new")
  val isNew: Boolean
)
