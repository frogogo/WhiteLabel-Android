package ru.frogogo.whitelabel.util.moshi.adapter

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import ru.frogogo.whitelabel.dictionary.VendingProductState

internal class VendingProductStateJsonAdapterTest {

  private val adapter = VendingProductStateJsonAdapter()

  @TestFactory
  fun `converter should convert states`(): List<DynamicTest> {
    val params = listOf(
      ConverterParam("available", VendingProductState.AVAILABLE),
      ConverterParam("already_received", VendingProductState.ALREADY_RECEIVED),
      ConverterParam("out_of_stock", VendingProductState.OUT_OF_STOCK),
      ConverterParam("unavailable", VendingProductState.UNAVAILABLE),
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
  fun `converter should throw exception when state can't be recognized`() {
    assertThrows<Exception> {
      adapter.fromJson("blahblah")
    }
  }

  private data class ConverterParam(
    val stateName: String,
    val expectedState: VendingProductState,
  )
}
