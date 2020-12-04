package ru.poprobuy.poprobuy.util

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import ru.poprobuy.poprobuy.util.ConfirmationCodeUtils.extractConfirmationCodeFromSms

class ConfirmationCodeUtilsTest {

  @TestFactory
  fun extractConfirmationCodeFromSms(): List<DynamicTest> {
    val tests = listOf(
      // Null sms
      ExtractCodeTestParams(null, null),
      // Ordinary sms
      run {
        val code = "1".repeat(Constants.CONFIRMATION_CODE_LENGTH)
        ExtractCodeTestParams("Poprobuy.ru code: $code", code)
      },
      // Sms with only code
      run {
        val code = "1".repeat(Constants.CONFIRMATION_CODE_LENGTH)
        ExtractCodeTestParams(code, code)
      },
      // Sms with code in center
      run {
        val code = "1".repeat(Constants.CONFIRMATION_CODE_LENGTH)
        ExtractCodeTestParams("Poprobuy.ru code: $code <- is here", code)
      },
      // Sms with longer code
      run {
        val code = "1".repeat(Constants.CONFIRMATION_CODE_LENGTH + 1)
        ExtractCodeTestParams("Poprobuy.ru code: $code", null)
      },
      // Sms with shorter code
      run {
        val code = "1".repeat(Constants.CONFIRMATION_CODE_LENGTH - 1)
        ExtractCodeTestParams("Poprobuy.ru code: $code", null)
      },
    )

    fun test(param: ExtractCodeTestParams) {
      extractConfirmationCodeFromSms(param.sms) shouldBeEqualTo param.code
    }

    return tests.mapIndexed { index, params ->
      DynamicTest.dynamicTest(index.toString()) {
        test(params)
      }
    }
  }

  private data class ExtractCodeTestParams(
    val sms: String?,
    val code: String?,
  )

}
