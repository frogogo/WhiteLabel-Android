@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di

import org.koin.dsl.module
import ru.frogogo.whitelabel.di.scope.couponCode
import ru.frogogo.whitelabel.di.scope.couponInfo
import ru.frogogo.whitelabel.di.scope.coupons
import ru.frogogo.whitelabel.di.scope.homeScope

val screenModule = module {
  homeScope()
  couponInfo()
  couponCode()
  coupons()
}
