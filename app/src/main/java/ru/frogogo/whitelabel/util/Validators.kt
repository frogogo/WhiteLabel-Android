package ru.frogogo.whitelabel.util

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import ru.frogogo.whitelabel.R

object Validators {

  private val PATTERN_NON_DIGITS by lazy { Regex("[^0-9]+") }
  private val PATTERN_DIGITS by lazy { Regex("\\d+") }
  private val PATTERN_USER_NAME by lazy { Regex("[A-Za-zА-ЯЁа-яё\\-]+") }

  private const val MIN_LENGTH_PHONE = 10

  @VisibleForTesting
  const val MIN_USER_NAME_LENGTH = 2

  @VisibleForTesting
  const val MAX_USER_NAME_LENGTH = 20

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

  /**
   * Checks vending machine validity
   * @return string res or null if all checks passed
   */
  @StringRes
  fun isVendingMachineNumber(text: String): Int? = when {
    text.isBlank() -> R.string.error_machine_number_empty
    else -> null
  }
}
