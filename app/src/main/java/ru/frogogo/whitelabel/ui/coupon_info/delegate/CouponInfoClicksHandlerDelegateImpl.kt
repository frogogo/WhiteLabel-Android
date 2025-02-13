package ru.frogogo.whitelabel.ui.coupon_info.delegate

import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.ui.AbstractViewModelNavigationDelegate
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.ui.coupon_info.CouponInfoEffect
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class CouponInfoClicksHandlerDelegateImpl(
  dispatchersProvider: DispatchersProvider,
  private val coupon: CouponUiModel,
  private val mutableEffectLiveEvent: LiveEvent<CouponInfoEffect>,
) : AbstractViewModelNavigationDelegate(dispatchersProvider),
  CouponInfoClicksHandlerDelegate {

  override fun onBackButtonClicked() {
    navigateBack()
  }

  override fun onShowCodeClicked() {
    mutableEffectLiveEvent.value = CouponInfoEffect.ShowCode(coupon.code)
  }
}
