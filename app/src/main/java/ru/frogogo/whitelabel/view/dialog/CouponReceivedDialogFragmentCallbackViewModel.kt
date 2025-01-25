package ru.frogogo.whitelabel.view.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.e
import ru.frogogo.whitelabel.core.Event
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.extension.asLiveData
import ru.frogogo.whitelabel.extension.setEvent

class CouponReceivedDialogFragmentCallbackViewModel : ViewModel() {

  private val _onShowClickedEvent = MutableLiveData<Event<CouponUiModel>>()
  val onShowClickedEvent = _onShowClickedEvent.asLiveData()

  fun onShow(coupon: CouponUiModel) {
    e { "Coupon1 $coupon" }
    _onShowClickedEvent.setEvent(coupon)
  }
}
