package ru.poprobuy.poprobuy.data.model.ui.machine

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class VendingMachineUiModel(
  val products: List<VendingProductUiModel>,
) : Parcelable
