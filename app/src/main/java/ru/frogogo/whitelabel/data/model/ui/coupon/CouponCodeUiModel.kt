package ru.frogogo.whitelabel.data.model.ui.coupon

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.frogogo.whitelabel.dictionary.CouponCodeType

@Parcelize
data class CouponCodeUiModel(
  val value: String,
  val type: CouponCodeType,
) : Parcelable
