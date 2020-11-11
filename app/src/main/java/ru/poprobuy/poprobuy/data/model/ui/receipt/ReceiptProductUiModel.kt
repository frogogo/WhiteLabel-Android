package ru.poprobuy.poprobuy.data.model.ui.receipt

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReceiptProductUiModel(
  val id: Int,
  val name: String,
  val imageUrl: String,
) : Parcelable
