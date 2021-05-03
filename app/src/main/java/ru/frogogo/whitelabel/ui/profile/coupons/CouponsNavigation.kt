package ru.frogogo.whitelabel.ui.profile.coupons

import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.extension.toCommand

interface CouponsNavigation {
  fun navigateToCouponInfo(coupon: CouponUiModel): NavigationCommand
}

class CouponsNavigationImpl : CouponsNavigation {

  override fun navigateToCouponInfo(coupon: CouponUiModel): NavigationCommand =
    CouponsFragmentDirections.couponsToCouponInfo(coupon).toCommand()
}
