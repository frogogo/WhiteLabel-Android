package ru.poprobuy.poprobuy.util.moshi.adapter

import org.amshove.kluent.AnyException
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldThrow
import org.junit.Test
import ru.poprobuy.poprobuy.dictionary.ReceiptState

class ReceiptStateJsonAdapterTest {

  private val adapter = ReceiptStateJsonAdapter()

  @Test
  fun `test to json conversion`() {
    adapter.toJson(ReceiptState.APPROVED) shouldBeEqualTo "approved"
  }

  @Test
  fun `test from json conversion`() {
    adapter.fromJson("approved") shouldBeEqualTo ReceiptState.APPROVED
  }

  @Test
  fun `test failure conversion`() {
    invoking { adapter.fromJson("blahblah") } shouldThrow AnyException
  }

}
