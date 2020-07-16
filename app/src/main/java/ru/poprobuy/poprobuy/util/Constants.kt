package ru.poprobuy.poprobuy.util

object Constants {

  const val LOCALE_DEFAULT = "ru"

  // Preferences
  internal const val KEY_ALIAS = "Poprobuy_key"

  // Urls
  internal const val HELP_SCAN_RECEIPT_URL = "https://poprobuy.ru" // TODO: 14.07.2020 Change url
  internal const val HELP_SCAN_MACHINE_URL = "https://google.com" // TODO: 14.07.2020 Change url

  // Auth
  const val PHONE_MASK = "([000]) [000]-[00]-[00]"
  const val PHONE_MASK_FULL = "+7 ([000]) [000]-[00]-[00]"
  const val PHONE_PREFIX = "+7"

  const val CONFIRMATION_CODE_LENGTH = 4

}
