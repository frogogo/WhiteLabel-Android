package ru.frogogo.whitelabel.ui.profile.coupons.delegate

import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel

interface CouponsClicksHandlerDelegate {

  fun onCouponClicked(coupon: CouponUiModel)

  fun onBackButtonClicked()
}
