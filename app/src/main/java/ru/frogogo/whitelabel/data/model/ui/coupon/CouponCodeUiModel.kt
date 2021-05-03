package ru.frogogo.whitelabel.data.model.ui.coupon

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CouponCodeUiModel(
  val value: String,
  val type: String,
) : Parcelable
