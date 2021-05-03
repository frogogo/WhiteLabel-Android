package ru.frogogo.whitelabel.ui.profile.coupons.delegate

import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.extension.isEmpty
import ru.frogogo.whitelabel.ui.profile.coupons.CouponsEffect
import ru.frogogo.whitelabel.ui.profile.coupons.data.CouponsDataFactory
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

private val errorState = emptyList<RecyclerViewItem>()

class CouponsStateHandlerDelegate(
  dispatchersProvider: DispatchersProvider,
  private val mutableDataLive: MutableLiveData<List<RecyclerViewItem>>,
  private val mutableIsLoadingLive: MutableLiveData<Boolean>,
  private val mutableEffectLiveEvent: LiveEvent<CouponsEffect>,
  private val dataFactory: CouponsDataFactory,
) : BaseViewModelDelegate(dispatchersProvider) {

  fun showLoader() {
    mutableIsLoadingLive.postValue(mutableDataLive.isEmpty() || mutableDataLive.value == errorState)
  }

  fun showData(coupons: List<CouponUiModel>) {
    mutableIsLoadingLive.value = false
    mutableDataLive.value = dataFactory.create(coupons)
  }

  fun showError() {
    mutableIsLoadingLive.value = false
    mutableEffectLiveEvent.value = CouponsEffect.ShowLoadingError
  }
}
