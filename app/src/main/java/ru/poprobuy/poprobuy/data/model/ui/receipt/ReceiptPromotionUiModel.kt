package ru.poprobuy.poprobuy.data.model.ui.receipt

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReceiptPromotionUiModel(
  val name: String,
) : Parcelable
