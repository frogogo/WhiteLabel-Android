package ru.poprobuy.poprobuy.data.model.ui.machine

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VendingMachineUiModel(
  val products: List<VendingProductUiModel>,
) : Parcelable
