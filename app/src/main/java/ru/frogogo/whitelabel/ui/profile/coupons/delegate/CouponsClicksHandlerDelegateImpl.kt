package ru.frogogo.whitelabel.ui.profile.coupons.delegate

import ru.frogogo.whitelabel.core.ui.AbstractViewModelNavigationDelegate
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.ui.profile.coupons.CouponsNavigation
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class CouponsClicksHandlerDelegateImpl(
  dispatchersProvider: DispatchersProvider,
  private val couponsNavigation: CouponsNavigation,
) : AbstractViewModelNavigationDelegate(dispatchersProvider),
  CouponsClicksHandlerDelegate {

  override fun onCouponClicked(coupon: CouponUiModel) {
    couponsNavigation.navigateToCouponInfo(coupon).navigate()
  }

  override fun onBackButtonClicked() {
    navigateBack()
  }
}
