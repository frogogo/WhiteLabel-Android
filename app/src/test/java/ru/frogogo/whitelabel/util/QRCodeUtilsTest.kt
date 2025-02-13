package ru.frogogo.whitelabel.util

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import ru.frogogo.whitelabel.util.QRCodeUtils.getQueryParametersMap
import ru.frogogo.whitelabel.util.QRCodeUtils.isQueryString

@RunWith(RobolectricTestRunner::class)
class QRCodeUtilsTest {

  @Test
  fun `getQueryParametersMap from query`() {
    getQueryParametersMap("a=1&b=2") shouldBeEqualTo mapOf("a" to "1", "b" to "2")
  }

  @Test
  fun `getQueryParametersMap from string`() {
    getQueryParametersMap("ABCDEF") shouldBeEqualTo mapOf()
  }

  @Test
  fun `getQueryParametersMap from empty string`() {
    getQueryParametersMap("") shouldBeEqualTo mapOf()
  }

  @Test
  fun `checks valid query string`() {
    isQueryString("a=1&b=2").shouldBeTrue()
  }

  @Test
  fun `checks invalid query string`() {
    isQueryString("ABCDEF").shouldBeFalse()
  }

  @Test
  fun `checks empty query string`() {
    isQueryString("").shouldBeFalse()
  }
}
