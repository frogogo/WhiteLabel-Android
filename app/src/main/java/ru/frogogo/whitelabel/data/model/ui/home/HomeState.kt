package ru.frogogo.whitelabel.data.model.ui.home

import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel

sealed class HomeState {

  object Empty : HomeState()

  data class Progress(
    val couponProgress: HomeCouponProgressUiModel,
    val coupons: List<CouponUiModel>,
    val receipts: List<ReceiptUiModel>,
  ) : HomeState()
}
