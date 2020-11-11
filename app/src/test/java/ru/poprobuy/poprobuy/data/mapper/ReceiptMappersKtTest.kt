package ru.poprobuy.poprobuy.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel

class ReceiptMappersKtTest {

  @Test
  fun `receipt maps to ui model`() {
    val receipt = DataFixtures.receipt
    receipt.toUiModel() shouldBeEqualTo ReceiptUiModel(
      id = receipt.id,
      number = receipt.number,
      date = receipt.timestamp,
      value = receipt.sum,
      state = receipt.state,
      promotion = null,
      product = null,
      rejectReason = null
    )
  }

}
