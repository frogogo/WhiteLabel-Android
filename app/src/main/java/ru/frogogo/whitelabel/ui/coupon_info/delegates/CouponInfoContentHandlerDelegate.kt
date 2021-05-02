package ru.frogogo.whitelabel.ui.coupon_info.delegates

import androidx.lifecycle.MutableLiveData
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class CouponInfoContentHandlerDelegate(
  dispatchersProvider: DispatchersProvider,
  private val coupon: CouponUiModel,
  private val mutableContentLive: MutableLiveData<CouponUiModel>,
) : BaseViewModelDelegate(dispatchersProvider) {

  fun postData() {
    mutableContentLive.value = coupon
  }
}
