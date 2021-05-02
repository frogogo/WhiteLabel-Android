package ru.frogogo.whitelabel.ui.coupon_info

import ru.frogogo.whitelabel.core.ui.BaseViewModel

class CouponInfoViewModel(
  liveData: LiveDataHolder,
  private val delegates: DelegatesHolder,
) : BaseViewModel() {

  data class LiveDataHolder(
    val stub: Int,
  )

  data class DelegatesHolder(
    val stub: Int,
  )
}
