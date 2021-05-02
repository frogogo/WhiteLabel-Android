package ru.frogogo.whitelabel.ui.home.delegate

import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel

interface HomeClickHandlerDelegate {

  fun onProfileClicked()

  fun onScanClicked()

  fun onCouponClicked(coupon: CouponUiModel)
}
