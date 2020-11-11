package ru.poprobuy.poprobuy.data.model.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.util.moshi.qualifier.ErrorReasonQualifier

@JsonClass(generateAdapter = true)
data class ErrorResponse(
  @ErrorReasonQualifier
  @Json(name = "error")
  val error: Int,
)

fun ErrorResponse?.getErrorOrDefault(): Int = this?.error ?: R.string.error_something_went_wrong
