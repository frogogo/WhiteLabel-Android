package ru.frogogo.whitelabel.extension

import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.util.ResourceProvider
import ru.frogogo.whitelabel.util.network.NetworkError

// TODO: 01.12.2020 Tests
fun ResourceProvider.errorOrDefault(error: String?): String =
  if (!error.isNullOrBlank()) error else getString(R.string.error_something_went_wrong)

fun ResourceProvider.errorOrDefault(error: NetworkError<ErrorResponse>): String {
  return when (error) {
    is NetworkError.HttpError -> errorOrDefault(error.data?.errorText)
    else -> getString(R.string.error_something_went_wrong)
  }
}
