package ru.frogogo.whitelabel.util

import ru.frogogo.whitelabel.BuildConfig

object Constants {

  const val LOCALE_DEFAULT = "ru"

  const val SHOP_NAME = "Семья"

  // Preferences
  const val KEY_ALIAS = "frogogo_key"

  // Network
  const val FROGOGO_API_ENDPOINT = "${BuildConfig.BASE_URL}/api/"
  const val FROGOGO_API_VERSION = BuildConfig.API_VERSION

  // Urls
  const val HELP_SCAN_RECEIPT_URL = "https://poprobuy.ru" // TODO: 14.07.2020 Change url

  // Auth
  const val SMS_SENDER_NAME = "CodoPhone" // TODO: 19.07.2020 Change to own sender

  const val PHONE_PREFIX = BuildConfig.PHONE_PREFIX
  const val PHONE_MASK = "([000]) [000]-[00]-[00]"
  const val PHONE_MASK_FULL = "$PHONE_PREFIX $PHONE_MASK"

  const val CONFIRMATION_CODE_LENGTH = 4
  const val VENDING_MACHINE_ID_MAX_LENGTH = 8
}
