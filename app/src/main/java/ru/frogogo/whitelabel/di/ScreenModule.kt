@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di

import org.koin.dsl.module
import ru.frogogo.whitelabel.di.scope.*

val screenModule = module {
  homeScope()
  couponInfo()
  couponCode()
  coupons()
  scanner()
  successScan()
}
