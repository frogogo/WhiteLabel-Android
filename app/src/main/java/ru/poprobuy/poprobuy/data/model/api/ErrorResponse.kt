package ru.poprobuy.poprobuy.data.model.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
  @Json(name = "error")
  val errorReason: String,
  @Json(name = "error_text")
  val errorText: String,
)
