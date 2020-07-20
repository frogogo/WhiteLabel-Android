package ru.poprobuy.poprobuy.data.model.api.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserUpdateRequest(
  @Json(name = "email")
  val email: String,
  @Json(name = "first_name")
  val firstName: String
)
