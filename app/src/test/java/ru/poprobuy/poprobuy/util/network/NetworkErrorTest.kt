package ru.poprobuy.poprobuy.util.network

import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class NetworkErrorTest {

  @Test
  fun `handles error with code`() {
    val error = NetworkError.HttpError(400, null)
    val callback: (NetworkError.HttpError<Nothing>) -> Unit = mockk(relaxed = true)

    error.onHttpErrorWithCode(400, callback)

    verify {
      callback(any())
    }
  }

  @Test
  fun `not handles error with another code`() {
    val error = NetworkError.HttpError(400, null)
    val callback: (NetworkError.HttpError<Nothing>) -> Unit = mockk(relaxed = true)

    error.onHttpErrorWithCode(404, callback)

    verify {
      callback wasNot Called
    }
  }

  @Test
  fun `test not handles not http error`() {
    val error = NetworkError.Unknown<Any>()
    val callback: (NetworkError.HttpError<Any>) -> Unit = mockk(relaxed = true)

    error.onHttpErrorWithCode(404, callback)

    verify {
      callback wasNot Called
    }
  }

}
