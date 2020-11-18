package ru.poprobuy.poprobuy.ui.profile.receipts

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldNotContain
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.model.ui.profile.receipts.ReceiptsEmptyState
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptsScanAvailable
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import ru.poprobuy.poprobuy.ui.profile.receipts.ReceiptsDataUtils.createReceiptsData
import ru.poprobuy.poprobuy.ui.profile.receipts.details.ReceiptDetailsButtonState

class ReceiptsDataUtilsTest {

  @Test
  fun `createReceiptsData should show empty state when list is empty`() {
    createReceiptsData(listOf()) shouldContain ReceiptsEmptyState
  }

  @Test
  fun `createReceiptsData adds scan available if no blocking states`() {
    val receipts = listOf(
      DataFixtures.getReceiptUIModel().copy(state = ReceiptState.COMPLETED)
    )
    createReceiptsData(receipts) shouldContain ReceiptsScanAvailable
  }

  @Test
  fun `createReceiptsData doesn't add scan available if there are blocking states`() {
    val receipts = listOf(
      DataFixtures.getReceiptUIModel().copy(state = ReceiptState.APPROVED)
    )
    createReceiptsData(receipts) shouldNotContain ReceiptsScanAvailable
  }

  @TestFactory
  fun getReceiptDetailsButtonState(): List<DynamicTest> {
    val params = listOf(
      // Receipt creation
      GetReceiptDetailsButtonStateParam(
        name = "creation should be allowed in completed state",
        receipts = listOf(
          DataFixtures.getReceiptUIModel().copy(state = ReceiptState.COMPLETED),
          DataFixtures.getReceiptUIModel().copy(state = ReceiptState.REJECTED),
        ),
        state = ReceiptDetailsButtonState(canCreateReceipt = true, canTakeProduct = false)
      ),
      GetReceiptDetailsButtonStateParam(
        name = "creation should be allowed in rejected state",
        receipts = listOf(
          DataFixtures.getReceiptUIModel().copy(state = ReceiptState.REJECTED),
          DataFixtures.getReceiptUIModel().copy(state = ReceiptState.APPROVED),
        ),
        state = ReceiptDetailsButtonState(canCreateReceipt = true, canTakeProduct = false)
      ),
      // Taking product
      GetReceiptDetailsButtonStateParam(
        name = "product taking should be allowed in approved state",
        receipts = listOf(
          DataFixtures.getReceiptUIModel().copy(state = ReceiptState.APPROVED),
          DataFixtures.getReceiptUIModel().copy(state = ReceiptState.REJECTED),
        ),
        state = ReceiptDetailsButtonState(canCreateReceipt = false, canTakeProduct = true)
      ),
      // Corner cases
      GetReceiptDetailsButtonStateParam(
        name = "empty list should restrict all actions",
        receipts = emptyList(),
        state = ReceiptDetailsButtonState(canCreateReceipt = false, canTakeProduct = false)
      ),
    )

    fun test(param: GetReceiptDetailsButtonStateParam) {
      ReceiptsDataUtils.getReceiptDetailsButtonState(param.receipts) shouldBeEqualTo param.state
    }

    return params.map { param ->
      DynamicTest.dynamicTest(param.name) {
        test(param)
      }
    }
  }

  private data class GetReceiptDetailsButtonStateParam(
    val name: String,
    val receipts: List<ReceiptUiModel>,
    val state: ReceiptDetailsButtonState,
  )

}
