package ru.frogogo.whitelabel.extension

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ru.frogogo.whitelabel.util.Constants

class StringExtKtTest {

  @Test
  fun `format formatted phone number`() {
    "+7 (916) 279-56-00".formatWithMask(Constants.PHONE_MASK_FULL) shouldBeEqualTo "+7 (916) 279-56-00"
  }

  @Test
  fun `format unformatted phone number`() {
    "+79162795600".formatWithMask(Constants.PHONE_MASK_FULL) shouldBeEqualTo "+7 (916) 279-56-00"
  }

  @Test
  fun `unformatted number full`() {
    "+7 (916) 123-45-67".getUnformattedPhoneNumber() shouldBeEqualTo "+79161234567"
  }

  @Test
  fun `unformatted  number without prefix`() {
    "(916) 123-45-67".getUnformattedPhoneNumber() shouldBeEqualTo "9161234567"
  }
}
