package ru.poprobuy.poprobuy.dictionary

import ru.poprobuy.poprobuy.R

enum class ErrorReason(
  val errorReason: String,
  val errorResId: Int
) {
  DEFAULT("something_went_wrong", R.string.error_something_went_wrong),
  RECEIPT_INVALID("qr_string_invalid", R.string.error_receipt_format),
  RECEIPT_NOT_UNIQUE("qr_string_not_unique", R.string.error_receipt_not_unique);

  companion object {
    fun valueOfOrDefault(error: String): ErrorReason = values().find { it.errorReason == error } ?: DEFAULT
  }
}
