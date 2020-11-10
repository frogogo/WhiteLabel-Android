package ru.poprobuy.poprobuy.data.model.api.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
  @Json(name = "email")
  val email: String,
  @Json(name = "first_name")
  val firstName: String,
  @Json(name = "phone_number")
  val phoneNumber: String,
)
