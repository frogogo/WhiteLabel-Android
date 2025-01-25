package ru.frogogo.whitelabel.ui.home.delegate

import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.ui.home.HomeEffect
import ru.frogogo.whitelabel.usecase.home.WasCouponReceivedUseCase
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class HomeCouponReceivedDelegate(
  dispatchers: DispatchersProvider,
  private val wasCouponReceivedUseCase: WasCouponReceivedUseCase,
  private val homeEffectLiveEvent: LiveEvent<HomeEffect>,
) : BaseViewModelDelegate(dispatchers) {

  fun showCouponReceived(state: HomeState) {
    wasCouponReceivedUseCase(state)?.let { coupon ->
      homeEffectLiveEvent.postValue(HomeEffect.ShowCouponReceived(coupon))
    }
  }
}
