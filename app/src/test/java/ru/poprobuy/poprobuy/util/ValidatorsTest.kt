package ru.poprobuy.poprobuy.util

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.util.Validators.MAX_USER_NAME_LENGTH
import ru.poprobuy.poprobuy.util.Validators.MIN_USER_NAME_LENGTH

@RunWith(RobolectricTestRunner::class)
class ValidatorsTest {

  // region isPhone

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

  // endregion

  // region isUserName

  @Test
  fun `isUserName allows valid name`() {
    Validators.isUserName("Alexey").shouldBeNull()
  }

  @Test
  fun `isUserName respects min length`() {
    Validators.isUserName("a".repeat(MIN_USER_NAME_LENGTH - 1)) shouldBeEqualTo R.string.error_user_name_length
  }

  @Test
  fun `isUserName respects max length`() {
    Validators.isUserName("a".repeat(MAX_USER_NAME_LENGTH + 1)) shouldBeEqualTo R.string.error_user_name_length
  }

  // TODO: 26.11.2020 isUserName regexp test

  // endregion

  // region isConfirmationCode

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

  // endregion

  // region isEmail

  // TODO: 26.11.2020 isEmail tests

  // endregion

  // region isVendingMachineNumber

  @Test
  fun `isVendingMachineNumber allows valid number`() {
    Validators.isVendingMachineNumber("54149115").shouldBeNull()
  }

  @Test
  fun `isVendingMachineNumber should not be empty`() {
    Validators.isVendingMachineNumber("") shouldBeEqualTo R.string.error_machine_number_empty
  }

  @Test
  fun `isVendingMachineNumber should not be blank`() {
    Validators.isVendingMachineNumber("  ") shouldBeEqualTo R.string.error_machine_number_empty
  }

  // endregion

}
