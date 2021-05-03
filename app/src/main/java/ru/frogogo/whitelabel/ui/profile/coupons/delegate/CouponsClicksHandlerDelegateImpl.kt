package ru.frogogo.whitelabel.ui.profile.coupons.delegate

import ru.frogogo.whitelabel.core.ui.AbstractViewModelNavigationDelegate
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class CouponsClicksHandlerDelegateImpl(
  dispatchersProvider: DispatchersProvider,
) : AbstractViewModelNavigationDelegate(dispatchersProvider),
  CouponsClicksHandlerDelegate {

  override fun onCouponClicked(coupon: CouponUiModel) {

  }

  override fun onBackButtonClicked() {
    navigateBack()
  }
}
