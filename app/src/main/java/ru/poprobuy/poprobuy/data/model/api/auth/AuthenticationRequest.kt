package ru.poprobuy.poprobuy.data.model.api.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticationRequest(
  @Json(name = "phone_number")
  val phoneNumber: String,
  @Json(name = "password")
  val password: String,
)
