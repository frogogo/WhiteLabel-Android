package ru.frogogo.whitelabel.data.model.ui.machine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import ru.frogogo.test.DataFixtures

internal class VendingProductUiModelTest {

  @TestFactory
  fun `product should be active`(): List<DynamicTest> {
    val params = listOf(
      IsActiveTestParam(VendingProductState.AVAILABLE, true),
      IsActiveTestParam(VendingProductState.ALREADY_RECEIVED, false),
      IsActiveTestParam(VendingProductState.OUT_OF_STOCK, false),
      IsActiveTestParam(VendingProductState.UNAVAILABLE, false),
    )

    fun test(param: IsActiveTestParam) {
      DataFixtures.getVendingProductUiModel(1).copy(state = param.state).isActive() shouldBeEqualTo param.expected
    }

    return params.map { param ->
      DynamicTest.dynamicTest(param.state.name) {
        test(param)
      }
    }
  }

  private data class IsActiveTestParam(
    val state: VendingProductState,
    val expected: Boolean,
  )
}
