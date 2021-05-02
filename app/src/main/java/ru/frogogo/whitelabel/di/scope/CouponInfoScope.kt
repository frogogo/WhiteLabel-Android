@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di.scope

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import ru.frogogo.whitelabel.ui.coupon_info.CouponInfoFragment
import ru.frogogo.whitelabel.ui.coupon_info.CouponInfoViewModel

fun Module.couponInfo() {
  scope<CouponInfoFragment> {
    // Core
    viewModel { CouponInfoViewModel(get(), get()) }

    // LiveData
    scoped {
      CouponInfoViewModel.LiveDataHolder(0)
    }

    // Delegates

    scoped { CouponInfoViewModel.DelegatesHolder(0) }
  }
}
