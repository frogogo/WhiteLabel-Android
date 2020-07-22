package ru.poprobuy.poprobuy.usecase

import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class UseCaseResultKtTest {

  @Test
  fun `onSuccess is called`() {
    val action = mockk<(Int) -> Unit>(relaxed = true)

    UseCaseResult.Success<Int, Unit>(1).onSuccess(action)

    verify {
      action(1)
    }
  }

  @Test
  fun `onSuccess is not called`() {
    val action = mockk<(Int) -> Unit>(relaxed = true)

    UseCaseResult.Failure<Int, Unit>().onSuccess(action)

    verify {
      action wasNot Called
    }
  }

  @Test
  fun `onFailure is called`() {
    val action = mockk<() -> Unit>(relaxed = true)

    UseCaseResult.Failure<Int, Unit>().onFailure(action)

    verify {
      action()
    }
  }

  @Test
  fun `onFailure is not called`() {
    val action = mockk<() -> Unit>(relaxed = true)

    UseCaseResult.Success<Int, Unit>(1).onFailure(action)

    verify {
      action wasNot Called
    }
  }

}
