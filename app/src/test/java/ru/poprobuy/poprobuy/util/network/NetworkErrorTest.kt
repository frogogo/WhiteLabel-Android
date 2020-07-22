package ru.poprobuy.poprobuy.util.network

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Test

class NetworkErrorTest {

  @Test
  fun `handles error with code`() {
    val error = NetworkError.HttpError(400, null)

    error.isHttpErrorWithCode(400).shouldBeTrue()
  }

  @Test
  fun `not handles error with another code`() {
    val error = NetworkError.HttpError(400, null)

    error.isHttpErrorWithCode(404).shouldBeFalse()
  }

  @Test
  fun `test not handles not http error`() {
    val error = NetworkError.Unknown<Any>()

    error.isHttpErrorWithCode(404).shouldBeFalse()
  }

}
