package ru.poprobuy.poprobuy.data.model.ui.machine

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VendingProductUiModel(
  val id: Int,
  val name: String,
  val imageUrl: String,
  val availableToTake: Boolean,
) : Parcelable
