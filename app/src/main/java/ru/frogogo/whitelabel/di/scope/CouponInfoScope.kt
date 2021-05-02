@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di.scope

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import ru.frogogo.whitelabel.ui.coupon_info.CouponInfoViewModel
import ru.frogogo.whitelabel.ui.home.HomeFragment

fun Module.couponInfo() {
  scope<HomeFragment> {
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
