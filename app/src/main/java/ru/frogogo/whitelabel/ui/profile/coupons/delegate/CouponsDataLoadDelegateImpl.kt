package ru.frogogo.whitelabel.ui.profile.coupons.delegate

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class CouponsDataLoadDelegateImpl(
  dispatchersProvider: DispatchersProvider,
  private val stateHandlerDelegate: CouponsStateHandlerDelegate,
) : BaseViewModelDelegate(dispatchersProvider),
  CouponsDataLoadDelegate {

  override fun refreshData() {
    scope.launch {
      stateHandlerDelegate.showLoader()

      // TODO: 03.05.2021 Real data
      @Suppress("detekt.MagicNumber")
      delay(1500)
      stateHandlerDelegate.showData(
        listOf(),
      )
    }
  }
}
