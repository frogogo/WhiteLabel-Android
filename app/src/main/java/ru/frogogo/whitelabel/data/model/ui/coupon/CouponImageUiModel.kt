package ru.frogogo.whitelabel.data.model.ui.coupon

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CouponImageUiModel(
  val largeUrl: String,
  val thumbUrl: String,
) : Parcelable
