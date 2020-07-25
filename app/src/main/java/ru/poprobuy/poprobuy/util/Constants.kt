package ru.poprobuy.poprobuy.util

import ru.poprobuy.poprobuy.BuildConfig

object Constants {

  const val LOCALE_DEFAULT = "ru"

  // Preferences
  const val KEY_ALIAS = "Poprobuy_key"

  // Network
  const val POPROBUY_API_ENDPOINT = "${BuildConfig.BASE_URL}/api/"
  const val POPROBUY_API_VERSION = BuildConfig.VERSION_CODE

  // Urls
  const val HELP_SCAN_RECEIPT_URL = "https://poprobuy.ru" // TODO: 14.07.2020 Change url
  const val HELP_SCAN_MACHINE_URL = "https://google.com" // TODO: 14.07.2020 Change url

  // Auth
  const val SMS_SENDER_NAME = "CodoPhone" // TODO: 19.07.2020 Change to own sender

  const val PHONE_MASK = "([000]) [000]-[00]-[00]"
  const val PHONE_MASK_FULL = "+7 ([000]) [000]-[00]-[00]"
  const val PHONE_PREFIX = "+7"

  const val CONFIRMATION_CODE_LENGTH = 4

}
