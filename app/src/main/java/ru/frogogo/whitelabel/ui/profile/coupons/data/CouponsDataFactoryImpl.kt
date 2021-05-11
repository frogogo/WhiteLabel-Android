package ru.frogogo.whitelabel.ui.profile.coupons.data

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.ui.profile.coupons.model.CouponsEmptyState

class CouponsDataFactoryImpl : CouponsDataFactory {

  override fun create(coupons: List<CouponUiModel>): List<RecyclerViewItem> =
    if (coupons.isNotEmpty()) {
      coupons
    } else {
      listOf(CouponsEmptyState)
    }
}
