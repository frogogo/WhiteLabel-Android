package ru.poprobuy.poprobuy.util

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Assert.assertEquals
import org.junit.Test

class PhoneUtilsTest {

  @Test
  fun `addPrefix add prefix`() {
    val result = PhoneUtils.addPrefix("9162795600")

    result shouldBeEqualTo "+79162795600"
  }

  @Test
  fun `addPrefix don't add second prefix`() {
    val result = PhoneUtils.addPrefix("+79162795600")

    result shouldBeEqualTo "+79162795600"
    assertEquals("+79162795600", result)
  }

}
