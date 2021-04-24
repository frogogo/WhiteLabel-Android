package ru.poprobuy.poprobuy.util.analytics

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
  WEB_VIEW,

  PRODUCTS,
  MACHINE_SELECT,
}

fun AnalyticsScreen.lowercaseName(): String = name.toLowerCase(Locale.ENGLISH)
