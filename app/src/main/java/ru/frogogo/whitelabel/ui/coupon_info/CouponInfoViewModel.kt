package ru.frogogo.whitelabel.ui.coupon_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.ui.coupon_info.delegate.CouponInfoClicksHandlerDelegate
import ru.frogogo.whitelabel.ui.coupon_info.delegate.CouponInfoClicksHandlerDelegateImpl
import ru.frogogo.whitelabel.ui.coupon_info.delegate.CouponInfoContentHandlerDelegate

class CouponInfoViewModel(
  liveData: LiveDataHolder,
  private val delegates: DelegatesHolder,
) : BaseViewModel(),
  CouponInfoClicksHandlerDelegate by delegates.clicksHandlerDelegate {

  val contentLive: LiveData<CouponUiModel> = liveData.mutableContentLive
  val effectLiveEvent: LiveData<CouponInfoEffect> = liveData.mutableEffectLiveEvent

  override fun onStart() {
    super.onStart()
    delegates.contentHandlerDelegate.postData()
  }

  override fun onCleared() {
    super.onCleared()
    delegates.clicksHandlerDelegate.cancelJob()
  }

  data class LiveDataHolder(
    val mutableContentLive: MutableLiveData<CouponUiModel>,
    val mutableEffectLiveEvent: LiveEvent<CouponInfoEffect>,
  )

  data class DelegatesHolder(
    val contentHandlerDelegate: CouponInfoContentHandlerDelegate,
    val clicksHandlerDelegate: CouponInfoClicksHandlerDelegateImpl,
  )
}
