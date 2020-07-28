package ru.poprobuy.poprobuy.util

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import ru.poprobuy.poprobuy.R

@RunWith(RobolectricTestRunner::class)
class ValidatorsTest {

  @Test
  fun `isPhone clears phone string allows valid phone number`() {
    Validators.isPhone("916 279 56 00").shouldBeNull()
  }

  @Test
  fun `isPhone respects min length`() {
    Validators.isPhone("916 279 56") shouldBeEqualTo R.string.error_phone_length
  }

  @Test
  fun `isPhone allows different formats`() {
    Validators.isPhone("+7 (916) 279-56-00").shouldBeNull()
  }

  @Test
  fun `isPhone allows only phone symbols`() {
    Validators.isPhone("a9162795600") shouldBeEqualTo R.string.error_phone_format
  }

  @Test
  fun `isConfirmationCode respects max length`() {
    val code = "1".repeat(Constants.CONFIRMATION_CODE_LENGTH + 1) // Generate longer code than allowed

    Validators.isConfirmationCode(code) shouldBeEqualTo R.string.error_confirmation_code_length
  }

  @Test
  fun `isConfirmationCode respects min length`() {
    val code = "1".repeat(Constants.CONFIRMATION_CODE_LENGTH - 1) // Generate shorter code than allowed

    Validators.isConfirmationCode(code) shouldBeEqualTo R.string.error_confirmation_code_length
  }

  @Test
  fun `isConfirmationCode allows proper code`() {
    val code = "1".repeat(Constants.CONFIRMATION_CODE_LENGTH)

    Validators.isConfirmationCode(code).shouldBeNull()
  }

  @Test
  fun `isConfirmationCode allows only digits`() {
    val code = "f".repeat(Constants.CONFIRMATION_CODE_LENGTH)

    Validators.isConfirmationCode(code) shouldBeEqualTo R.string.error_confirmation_code_format
  }

}
