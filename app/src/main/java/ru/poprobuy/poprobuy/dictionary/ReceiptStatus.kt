package ru.poprobuy.poprobuy.dictionary

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.poprobuy.poprobuy.R

enum class ReceiptStatus {
  CHECK,
  ACCEPTED,
  REJECTED,
  COMPLETED;

  /**
   * @return color resource for given [ReceiptStatus]
   */
  @ColorRes
  fun getColor(): Int = when (this) {
    CHECK -> R.color.receipt_status_check
    ACCEPTED -> R.color.receipt_status_accepted
    REJECTED -> R.color.receipt_status_rejected
    COMPLETED -> R.color.receipt_status_completed
  }

  /**
   * @return drawable icon resource for given [ReceiptStatus]
   */
  @DrawableRes
  fun getHeaderIcon(): Int = when (this) {
    CHECK -> R.drawable.ic_receipt_check
    ACCEPTED -> R.drawable.ic_receipt_accepted
    REJECTED -> R.drawable.ic_receipt_rejected
    COMPLETED -> R.drawable.ic_receipt_completed
  }

  /**
   * @return string name resource for given [ReceiptStatus]
   */
  @StringRes
  fun getName(): Int = when (this) {
    CHECK -> R.string.receipt_status_check
    ACCEPTED -> R.string.receipt_status_accepted
    REJECTED -> R.string.receipt_status_rejected
    COMPLETED -> R.string.receipt_status_completed
  }

  /**
   * @return string subtitle for given [ReceiptStatus]
   */
  fun getStatusSubtitle(context: Context): String = when (this) {
    CHECK -> context.getString(R.string.receipt_status_check_description)
    ACCEPTED -> context.getString(R.string.receipt_status_accepted_description)
    REJECTED -> "" // TODO: 03.07.2020
    COMPLETED -> "" // TODO: 23.07.2020
  }

}
