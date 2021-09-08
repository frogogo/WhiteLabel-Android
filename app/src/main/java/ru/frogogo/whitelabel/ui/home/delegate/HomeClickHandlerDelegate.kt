package ru.frogogo.whitelabel.ui.home.delegate

import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.data.model.ui.item.ItemUiModel

interface HomeClickHandlerDelegate {

  fun onProfileClicked()

  fun onScanClicked()

  fun onCouponClicked(coupon: CouponUiModel)

  fun onItemClicked(item: ItemUiModel)

  fun onReceiptsButtonClicked()
}
