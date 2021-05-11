package ru.frogogo.whitelabel.ui.profile.coupons.data

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel

interface CouponsDataFactory {

  fun create(coupons: List<CouponUiModel>): List<RecyclerViewItem>
}
