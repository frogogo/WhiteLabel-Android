package ru.poprobuy.poprobuy.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ru.poprobuy.test.DataFixtures
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptDistributionNetworkUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptProductUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptRejectReasonUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel

class ReceiptMappersKtTest {

  @Test
  fun `receipt maps to ui model`() {
    val receipt = DataFixtures.getReceipt()
    receipt.toDomain() shouldBeEqualTo ReceiptUiModel(
      id = receipt.id,
      number = receipt.number,
      date = receipt.timestamp,
      value = receipt.sum,
      state = receipt.state,
      distributionNetwork = null,
      product = null,
      rejectReason = null
    )
  }

  @Test
  fun `ReceiptRejectReason maps to domain model`() {
    val rejectReason = DataFixtures.getReceiptRejectReason()

    rejectReason.toDomain() shouldBeEqualTo ReceiptRejectReasonUiModel(
      reason = rejectReason.reason,
      reasonText = rejectReason.reasonText
    )
  }

  @Test
  fun `ReceiptDistributionNetwork maps to domain model`() {
    val network = DataFixtures.getReceiptDistributionNetwork()

    network.toDomain() shouldBeEqualTo ReceiptDistributionNetworkUiModel(
      name = network.name
    )
  }

  @Test
  fun `ReceiptProduct maps to domain model`() {
    val product = DataFixtures.getReceiptProduct()

    product.toDomain() shouldBeEqualTo ReceiptProductUiModel(
      id = product.id,
      name = product.name,
      imageUrl = product.imageUrl
    )
  }
}
