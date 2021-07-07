package ru.frogogo.whitelabel.ui.promotion_items

import ru.frogogo.whitelabel.data.model.ui.coupon.CouponCodeUiModel

sealed class PromotionItemsEffect {

  data class ShowCode(
    val code: CouponCodeUiModel,
  ) : PromotionItemsEffect()
}
