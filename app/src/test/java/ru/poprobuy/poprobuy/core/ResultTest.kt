package ru.poprobuy.poprobuy.core

import io.mockk.Called
import io.mockk.spyk
import io.mockk.verify
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test

class ResultTest {

  @Test
  fun `successOrNull returns result`() {
    Result.Success<Int, String>(1).successOrNull() shouldBeEqualTo 1
  }

  @Test
  fun `successOrNull returns null`() {
    Result.Failure<Int, String>("1").successOrNull().shouldBeNull()
  }

  @Test
  fun `handle onSuccess is called`() {
    val onSuccess = spyk<(Int) -> Unit>({})
    val onFailure = spyk<(String) -> Unit>({})

    val result = Result.Success<Int, String>(1)

    result.handle(onFailure, onSuccess)

    verify {
      onSuccess.invoke(1)
      onFailure wasNot Called
    }
  }

  @Test
  fun `handle onFailure is called`() {
    val onSuccess = spyk<(Int) -> Unit>({})
    val onFailure = spyk<(String) -> Unit>({})

    val result = Result.Failure<Int, String>("1")

    result.handle(onFailure, onSuccess)

    verify {
      onSuccess wasNot Called
      onFailure.invoke("1")
    }
  }

  @Test
  fun `result isSuccess returns true`() {
    val result = Result.Success<Int, String>(1)

    result.isSuccess().shouldBeTrue()
  }

  @Test
  fun `result isSuccess returns false`() {
    val result = Result.Failure<Int, String>("1")

    result.isSuccess().shouldBeFalse()
  }

  @Test
  fun `doOnSuccess should be invoked on success`() {
    val onSuccess = spyk<(Int) -> Unit>({})
    val result = Result.Success<Int, String>(1)

    result.doOnSuccess(onSuccess) shouldBeEqualTo result

    verify {
      onSuccess(1)
    }
  }

  @Test
  fun `doOnSuccess shouldn't be invoked on failure`() {
    val onSuccess = spyk<(Int) -> Unit>({})
    val result = Result.Failure<Int, String>("1")

    result.doOnSuccess(onSuccess) shouldBeEqualTo result

    verify {
      onSuccess wasNot Called
    }
  }

  @Test
  fun `doOnFailure should be invoked on failure`() {
    val onFailure = spyk<(String) -> Unit>({})
    val result = Result.Failure<Int, String>("1")

    result.doOnFailure(onFailure) shouldBeEqualTo result

    verify {
      onFailure("1")
    }
  }

  @Test
  fun `doOnFailure shouldn't be invoked on success`() {
    val onFailure = spyk<(String) -> Unit>({})
    val result = Result.Success<Int, String>(1)

    result.doOnFailure(onFailure) shouldBeEqualTo result

    verify {
      onFailure wasNot Called
    }
  }
}
