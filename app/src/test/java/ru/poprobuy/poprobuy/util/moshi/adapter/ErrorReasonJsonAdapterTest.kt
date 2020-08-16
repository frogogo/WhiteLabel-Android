package ru.poprobuy.poprobuy.util.moshi.adapter

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeGreaterThan
import org.junit.Test
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.dictionary.ErrorReason

class ErrorReasonJsonAdapterTest {

  @Test
  fun `fromJson always returns string resource`() {
    ErrorReasonJsonAdapter().fromJson("not_existing_error") shouldBeGreaterThan 1
  }

  @Test
  fun `toJson always return value`() {
    ErrorReasonJsonAdapter().toJson(R.string.error_something_went_wrong) shouldBeEqualTo ErrorReason.DEFAULT.errorReason
  }

}
