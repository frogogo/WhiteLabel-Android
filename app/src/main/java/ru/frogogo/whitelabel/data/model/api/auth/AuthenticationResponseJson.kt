package ru.frogogo.whitelabel.data.model.api.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticationResponseJson(
  @Json(name = "email")
  val email: String?,
  @Json(name = "first_name")
  val firstName: String?,
  @Json(name = "phone_number")
  val phoneNumber: String,
  @Json(name = "jwt")
  val jwt: String,
  @Json(name = "refresh_token")
  val refreshToken: String,
  @Json(name = "is_new")
  val isNew: Boolean,
)
