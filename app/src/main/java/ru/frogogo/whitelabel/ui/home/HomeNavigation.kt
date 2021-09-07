package ru.frogogo.whitelabel.ui.home

import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomePromotionUiModel
import ru.frogogo.whitelabel.data.model.ui.item.ItemUiModel
import ru.frogogo.whitelabel.extension.toCommand

interface HomeNavigation {
  fun navigateToProfile(): NavigationCommand
  fun navigateToReceiptScan(): NavigationCommand
  fun navigateToReceipts(): NavigationCommand
  fun navigateToCouponInfo(coupon: CouponUiModel): NavigationCommand
  fun navigateToItemInfo(item: ItemUiModel): NavigationCommand
}

class HomeNavigationImpl : HomeNavigation {

  override fun navigateToProfile(): NavigationCommand =
    HomeFragmentDirections.homeToProfile().toCommand()

  override fun navigateToReceiptScan(): NavigationCommand =
    HomeFragmentDirections.homeToScanner().toCommand()

  override fun navigateToReceipts(): NavigationCommand =
    HomeFragmentDirections.homeToReceipts().toCommand()

  override fun navigateToCouponInfo(coupon: CouponUiModel): NavigationCommand =
    HomeFragmentDirections.homeToCouponInfo(coupon).toCommand()

  override fun navigateToItemInfo(item: ItemUiModel): NavigationCommand =
    HomeFragmentDirections.homeToItemInfo(item.id).toCommand()
}
