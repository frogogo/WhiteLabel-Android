package ru.poprobuy.poprobuy.dictionary

import ru.poprobuy.poprobuy.R

@Suppress("unused")
enum class ReceiptRejectReason(
  val errorReason: String,
  val errorResId: Int
) {
  DEFAULT("system_error", R.string.error_receipt_reject_reason_system_error),
  INVALID_DATE("invalid_date", R.string.error_receipt_reject_reason_invalid_date),
  INVALID_SUM("invalid_sum", R.string.error_receipt_reject_reason_invalid_sum),
  INVALID_TYPE("invalid_type", R.string.error_receipt_reject_reason_invalid_type),
  INVALID_DATA("invalid_data", R.string.error_receipt_reject_reason_invalid_data),
  DUPLICATE("duplicate", R.string.error_receipt_reject_reason_duplicate);

  companion object {
    fun valueOfOrDefault(error: String): ReceiptRejectReason = values().find { it.errorReason == error } ?: DEFAULT
  }
}
