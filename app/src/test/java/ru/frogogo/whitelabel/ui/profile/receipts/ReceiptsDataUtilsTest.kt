package ru.frogogo.whitelabel.ui.profile.receipts

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import ru.frogogo.test.DataFixtures
import ru.frogogo.whitelabel.data.model.ui.profile.receipts.ReceiptsEmptyState
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.dictionary.ReceiptState
import ru.frogogo.whitelabel.ui.profile.receipts.ReceiptsDataUtils.createReceiptsData
import ru.frogogo.whitelabel.ui.profile.receipts.details.ReceiptDetailsButtonState

class ReceiptsDataUtilsTest {

  @Test
  fun `createReceiptsData should show empty state when list is empty`() {
    createReceiptsData(listOf()) shouldContain ReceiptsEmptyState
  }

  @TestFactory
  fun getReceiptDetailsButtonState(): List<DynamicTest> {
    val params = listOf(
      // Receipt creation
      GetReceiptDetailsButtonStateParam(
        name = "creation should be allowed in completed state",
        receipts = listOf(
          DataFixtures.getReceiptUIModel().copy(state = ReceiptState.APPROVED),
          DataFixtures.getReceiptUIModel().copy(state = ReceiptState.REJECTED),
        ),
        state = ReceiptDetailsButtonState(canCreateReceipt = true, canTakeProduct = false),
      ),
      GetReceiptDetailsButtonStateParam(
        name = "creation should be allowed in rejected state",
        receipts = listOf(
          DataFixtures.getReceiptUIModel().copy(state = ReceiptState.REJECTED),
          DataFixtures.getReceiptUIModel().copy(state = ReceiptState.APPROVED),
        ),
        state = ReceiptDetailsButtonState(canCreateReceipt = true, canTakeProduct = false),
      ),
      // Taking product
      GetReceiptDetailsButtonStateParam(
        name = "product taking should be allowed in approved state",
        receipts = listOf(
          DataFixtures.getReceiptUIModel().copy(state = ReceiptState.APPROVED),
          DataFixtures.getReceiptUIModel().copy(state = ReceiptState.REJECTED),
        ),
        state = ReceiptDetailsButtonState(canCreateReceipt = false, canTakeProduct = true),
      ),
      // Corner cases
      GetReceiptDetailsButtonStateParam(
        name = "empty list should restrict all actions",
        receipts = emptyList(),
        state = ReceiptDetailsButtonState(canCreateReceipt = false, canTakeProduct = false),
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
