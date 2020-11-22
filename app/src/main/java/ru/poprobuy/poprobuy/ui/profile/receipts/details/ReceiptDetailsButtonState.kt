package ru.poprobuy.poprobuy.ui.profile.receipts.details

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ReceiptDetailsButtonState(
  val canCreateReceipt: Boolean,
  val canTakeProduct: Boolean,
) : Parcelable
