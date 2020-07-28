package ru.poprobuy.poprobuy.util

import android.util.Patterns
import androidx.annotation.StringRes
import ru.poprobuy.poprobuy.R

object Validators {

  private val PATTERN_NON_DIGITS = Regex("[^0-9]+")
  private val PATTERN_DIGITS = Regex("\\d+")
  private val PATTERN_USER_NAME = Regex("[A-Za-zА-Яа-я\\-]+")

  private const val MIN_LENGTH_PHONE = 10

  private const val MIN_USER_NAME_LENGTH = 2
  private const val MAX_USER_NAME_LENGTH = 20

  /**
   * Checks phone number validity
   * @return string res or null if all checks passed
   */
  @StringRes
  fun isPhone(text: String): Int? = when {
    // Check length
    text.replace(PATTERN_NON_DIGITS, "").length < MIN_LENGTH_PHONE -> R.string.error_phone_length
    // Check format
    !Patterns.PHONE.matcher(text).matches() -> R.string.error_phone_format
    // All is ok
    else -> null
  }

  /**
   * Checks user name validity
   * @return string res or null if all checks passed
   */
  @StringRes
  fun isUserName(text: String): Int? = when {
    // Check length
    text.length !in MIN_USER_NAME_LENGTH..MAX_USER_NAME_LENGTH -> R.string.error_user_name_length
    // Check format
    !PATTERN_USER_NAME.matches(text) -> R.string.error_user_name_format
    // All is ok
    else -> null
  }

  /**
   * Checks confirmation code validity
   * @return string res or null if all checks passed
   */
  @StringRes
  fun isConfirmationCode(text: String): Int? = when {
    // Check length
    text.length != Constants.CONFIRMATION_CODE_LENGTH -> R.string.error_confirmation_code_length
    // Check format
    !PATTERN_DIGITS.matches(text) -> R.string.error_confirmation_code_format
    // All is ok
    else -> null
  }

  /**
   * Checks email validity
   * @return string res or null if all checks passed
   */
  @StringRes
  fun isEmail(text: String): Int? = when {
    // Check format
    !Patterns.EMAIL_ADDRESS.matcher(text).matches() -> R.string.error_email_format
    // All is ok
    else -> null
  }

}
