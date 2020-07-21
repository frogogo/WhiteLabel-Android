package ru.poprobuy.poprobuy.extension

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class PairExtKtTest {

  @Test
  fun `pair converted to tripe with extension`() {
    1 to 2 to 3 shouldBeEqualTo Triple(1, 2, 3)
  }

}
