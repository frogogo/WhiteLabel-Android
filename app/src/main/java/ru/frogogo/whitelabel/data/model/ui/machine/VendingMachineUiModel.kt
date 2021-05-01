package ru.frogogo.whitelabel.data.model.ui.machine

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class VendingMachineUiModel(
  val id: Int,
  val cells: List<VendingCellUiModel>,
  val assignExpiresIn: Int,
) : Parcelable
