package ru.poprobuy.poprobuy.util

object ConfirmationCodeUtils {

  /**
   * Extracts confirmation code from given [message]
   */
  fun extractConfirmationCodeFromSms(message: String?): String? {
    message ?: return null

    // Build regexp - \b\d{4}\b
    val regexp = Regex("\\b\\d{" + Constants.CONFIRMATION_CODE_LENGTH + "}\\b")

    return regexp.find(message)?.value
  }

}
