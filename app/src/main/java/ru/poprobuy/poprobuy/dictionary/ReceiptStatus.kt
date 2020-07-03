package ru.poprobuy.poprobuy.dictionary

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.poprobuy.poprobuy.R

enum class ReceiptStatus {
  ACCEPTED,
  REJECTED,
  CHECK;

  /**
   * @return color resource for given [ReceiptStatus]
   */
  @ColorRes
  fun getHeaderColor(): Int = when (this) {
    ACCEPTED -> R.color.receipt_header_accept
    REJECTED -> R.color.receipt_header_reject
    CHECK -> R.color.receipt_header_check
  }

  /**
   * @return drawable icon resource for given [ReceiptStatus]
   */
  @DrawableRes
  fun getIcon(): Int = when (this) {
    ACCEPTED -> R.drawable.ic_receipt_accept
    REJECTED -> R.drawable.ic_receipt_reject
    CHECK -> R.drawable.ic_receipt_check
  }

  /**
   * @return string name resource for given [ReceiptStatus]
   */
  @StringRes
  fun getName(): Int = when (this) {
    ACCEPTED -> R.string.receipt_status_accept
    REJECTED -> R.string.receipt_status_reject
    CHECK -> R.string.receipt_status_check
  }

  /**
   * @return string subtitle for given [ReceiptStatus]
   */
  fun getStatusSubtitle(context: Context): String = when (this) {
    ACCEPTED -> context.getString(R.string.receipt_status_accept_description)
    REJECTED -> "" // TODO: 03.07.2020
    CHECK -> context.getString(R.string.receipt_status_check_description)
  }

}
