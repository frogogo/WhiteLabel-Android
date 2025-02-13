package ru.frogogo.whitelabel.ui.home.delegate

import com.github.ajalt.timberkt.d
import ru.frogogo.whitelabel.core.ui.AbstractViewModelNavigationDelegate
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.data.model.ui.item.ItemUiModel
import ru.frogogo.whitelabel.ui.home.HomeNavigation
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class HomeClickHandlerDelegateImpl(
  dispatchersProvider: DispatchersProvider,
  private val navigation: HomeNavigation,
) : AbstractViewModelNavigationDelegate(dispatchersProvider),
  HomeClickHandlerDelegate {

  override fun onProfileClicked() {
    d { "Navigating to profile" }
    navigation.navigateToProfile().navigate()
  }

  override fun onScanClicked() {
    d { "Navigating to receipt scan" }
    navigation.navigateToReceiptScan().navigate()
  }

  override fun onCouponClicked(coupon: CouponUiModel) {
    d { "Navigating to coupon info" }
    navigation.navigateToCouponInfo(coupon).navigate()
  }

  override fun onItemClicked(item: ItemUiModel) {
    d { "Navigating to item info" }
    navigation.navigateToItemInfo(item).navigate()
  }

  override fun onReceiptsButtonClicked() {
    d { "Navigating to receipts" }
    navigation.navigateToReceipts().navigate()
  }
}
