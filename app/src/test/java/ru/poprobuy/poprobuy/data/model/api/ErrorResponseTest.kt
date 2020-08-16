package ru.poprobuy.poprobuy.data.model.api

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import ru.poprobuy.poprobuy.R

class ErrorResponseTest {

  @Test
  fun `getErrorOrDefault returns error`() {
    val error = R.string.error_something_went_wrong
    ErrorResponse(error).getErrorOrDefault() shouldBeEqualTo error
  }

  @Test
  fun `getErrorOrDefault returns default error on null`() {
    null.getErrorOrDefault() shouldBeEqualTo R.string.error_something_went_wrong
  }

}
