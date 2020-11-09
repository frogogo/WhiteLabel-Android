package ru.poprobuy.poprobuy.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.model.ui.ReceiptUiModel

class ReceiptMappersKtTest {

  @Test
  fun `receipt maps to ui model`() {
    val receipt = DataFixtures.receipt
    receipt.toUiModel() shouldBeEqualTo ReceiptUiModel(
      number = receipt.number,
      value = receipt.sum,
      date = receipt.timestamp,
      state = receipt.state,
      shopName = null
    )
  }

}
