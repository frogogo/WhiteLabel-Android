package ru.poprobuy.poprobuy.extension

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.util.ResourceProvider
import ru.poprobuy.poprobuy.util.network.NetworkError

// TODO: 01.12.2020 Tests
fun ResourceProvider.errorOrDefault(error: String?): String =
  if (!error.isNullOrBlank()) error else getString(R.string.error_something_went_wrong)

fun ResourceProvider.errorOrDefault(error: NetworkError<ErrorResponse>): String {
  return when (error) {
    is NetworkError.HttpError -> errorOrDefault(error.data?.errorText)
    else -> getString(R.string.error_something_went_wrong)
  }
}
