package ru.frogogo.whitelabel.util.analytics

import java.util.*

enum class AnalyticsScreen {
  SPLASH,
  ONBOARDING,

  AUTH_POLICY,
  AUTH_PHONE,
  AUTH_CODE,
  AUTH_EMAIL,
  AUTH_NAME,

  HOME,
  PROFILE,
  SCANNER,
  RECEIPTS,
  COUPONS,
  WEB_VIEW,

  COUPON_INFO,
}

fun AnalyticsScreen.lowercaseName(): String = name.toLowerCase(Locale.ENGLISH)
