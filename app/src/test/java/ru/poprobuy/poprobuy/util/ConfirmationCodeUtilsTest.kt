package ru.poprobuy.poprobuy.util

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Test
import ru.poprobuy.poprobuy.util.ConfirmationCodeUtils.extractConfirmationCodeFromSms

class ConfirmationCodeUtilsTest {

  @Test
  fun `extract code returns null from null message`() {
    extractConfirmationCodeFromSms(null).shouldBeNull()
  }

  @Test
  fun `extract code`() {
    val code = "1".repeat(Constants.CONFIRMATION_CODE_LENGTH)
    extractConfirmationCodeFromSms("Poprobuy.ru code: $code") shouldBeEqualTo code
  }

  @Test
  fun `extract code from middle`() {
    val code = "1".repeat(Constants.CONFIRMATION_CODE_LENGTH)
    extractConfirmationCodeFromSms("Poprobuy.ru code: $code <- is here") shouldBeEqualTo code
  }

  @Test
  fun `extract code doesn't extract more symbols`() {
    val code = "1".repeat(Constants.CONFIRMATION_CODE_LENGTH + 1)
    extractConfirmationCodeFromSms("Poprobuy.ru code: $code").shouldBeNull()
  }

  @Test
  fun `extract code doesn't extract less symbols`() {
    val code = "1".repeat(Constants.CONFIRMATION_CODE_LENGTH - 1)
    extractConfirmationCodeFromSms("Poprobuy.ru code: $code").shouldBeNull()
  }

}
