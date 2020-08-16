package ru.poprobuy.poprobuy.dictionary

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class ErrorReasonTest {

  @Test
  fun `valueOfOrDefault returns value on valid reason`() {
    ErrorReason.valueOfOrDefault(ErrorReason.RECEIPT_INVALID.errorReason) shouldBeEqualTo ErrorReason.RECEIPT_INVALID
  }

  @Test
  fun `valueOfOrDefault returns default on invalid error `() {
    ErrorReason.valueOfOrDefault("not_existing_error_reason") shouldBeEqualTo ErrorReason.DEFAULT
  }

}
