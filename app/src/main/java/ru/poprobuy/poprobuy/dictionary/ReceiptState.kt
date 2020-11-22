package ru.poprobuy.poprobuy.dictionary

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.poprobuy.poprobuy.R

enum class ReceiptState {
  PROCESSING,
  APPROVED,
  REJECTED,
  COMPLETED;

  /**
   * @return color resource for given [ReceiptState]
   */
  @ColorRes
  fun getColor(): Int = when (this) {
    PROCESSING -> R.color.receipt_state_processing
    APPROVED -> R.color.receipt_state_approved
    REJECTED -> R.color.receipt_state_rejected
    COMPLETED -> R.color.receipt_state_completed
  }

  /**
   * @return drawable icon resource for given [ReceiptState]
   */
  @DrawableRes
  fun getHeaderIcon(): Int = when (this) {
    PROCESSING -> R.drawable.ic_receipt_processing
    APPROVED -> R.drawable.ic_receipt_approved
    REJECTED -> R.drawable.ic_receipt_rejected
    COMPLETED -> R.drawable.ic_receipt_completed
  }

  /**
   * @return string name resource for given [ReceiptState]
   */
  @StringRes
  fun getName(): Int = when (this) {
    PROCESSING -> R.string.receipt_state_processing
    APPROVED -> R.string.receipt_state_approved
    REJECTED -> R.string.receipt_state_rejected
    COMPLETED -> R.string.receipt_state_completed
  }

}
