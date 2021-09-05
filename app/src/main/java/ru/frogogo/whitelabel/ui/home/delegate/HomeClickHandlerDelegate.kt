package ru.frogogo.whitelabel.ui.home.delegate

import ru.frogogo.whitelabel.data.model.ui.coupon.CouponUiModel
import ru.frogogo.whitelabel.data.model.ui.home.HomePromotionUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel

interface HomeClickHandlerDelegate {

  fun onProfileClicked()

  fun onScanClicked()

  fun onCouponClicked(coupon: CouponUiModel)

  fun onReceiptsButtonClicked()

  fun onItemButtonClicked(promotion: HomePromotionUiModel)
}
