package ru.frogogo.whitelabel.data.model.ui.receipt

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReceiptDistributionNetworkUiModel(
  val name: String,
) : Parcelable
