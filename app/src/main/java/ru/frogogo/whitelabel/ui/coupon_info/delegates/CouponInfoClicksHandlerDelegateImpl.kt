package ru.frogogo.whitelabel.ui.coupon_info.delegates

import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class CouponInfoClicksHandlerDelegateImpl(
  dispatchersProvider: DispatchersProvider,
) : BaseViewModelDelegate(dispatchersProvider),
  CouponInfoClicksHandlerDelegate {

  override fun onBackButtonClicked() {

  }

  override fun onShowQrCodeClicked() {

  }
}
