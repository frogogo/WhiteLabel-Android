package ru.poprobuy.poprobuy.data.model.api.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticationResponse(
  // Auth Data
  @Json(name = "jwt")
  val accessToken: String,
  @Json(name = "is_new")
  val isNew: Boolean,

  // User Data
  @Json(name = "phone_number")
  val phoneNumber: String,
  @Json(name = "first_name")
  val firstName: String?,
  @Json(name = "email")
  val email: String?
)
