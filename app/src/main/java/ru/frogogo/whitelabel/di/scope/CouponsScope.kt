package ru.frogogo.whitelabel.di.scope

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import ru.frogogo.whitelabel.ui.profile.coupons.CouponsFragment
import ru.frogogo.whitelabel.ui.profile.coupons.CouponsViewModel

fun Module.coupons() {
  scope<CouponsFragment> {
    viewModel { CouponsViewModel() }
  }
}
