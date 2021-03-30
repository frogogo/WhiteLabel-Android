package ru.poprobuy.poprobuy.util.moshi.adapter

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import ru.poprobuy.poprobuy.dictionary.ReceiptState

class ReceiptStateJsonAdapterTest {

  private val adapter = ReceiptStateJsonAdapter()

  @TestFactory
  fun `test from json conversion`(): List<DynamicTest> {
    val params = listOf(
      ConverterParam("processing", ReceiptState.PROCESSING),
      ConverterParam("approved", ReceiptState.APPROVED),
      ConverterParam("rejected", ReceiptState.REJECTED),
      ConverterParam("completed", ReceiptState.COMPLETED),
    )

    fun test(param: ConverterParam) {
      adapter.fromJson(param.stateName) shouldBeEqualTo param.expectedState
    }

    return params.map { param ->
      DynamicTest.dynamicTest(param.expectedState.name) {
        test(param)
      }
    }
  }

  @Test
  fun `test failure conversion`() {
    assertThrows<Exception> {
      adapter.fromJson("blahblah")
    }
  }

  private data class ConverterParam(
    val stateName: String,
    val expectedState: ReceiptState,
  )
}
