package ru.poprobuy.poprobuy.data.model.api.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfirmationCodeRequest(
  @Json(name = "phone_number")
  val phoneNumber: String,
)
