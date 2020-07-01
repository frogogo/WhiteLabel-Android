package ru.poprobuy.poprobuy.util

object PhoneUtils {

  fun addPrefix(phoneNumber: String): String = if (phoneNumber.startsWith(Constants.PHONE_PREFIX)) {
    phoneNumber
  } else {
    "${Constants.PHONE_PREFIX} $phoneNumber"
  }

}
