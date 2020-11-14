package ru.poprobuy.poprobuy.ui.profile.receipts

import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldNotContain
import org.junit.Test
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.model.ui.profile.receipts.ReceiptsEmptyState
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptsScanAvailable
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import ru.poprobuy.poprobuy.ui.profile.receipts.ReceiptsDataFactory.createReceiptsData

class ReceiptsDataFactoryTest {

  @Test
  fun `factory should show empty state when list is empty`() {
    createReceiptsData(listOf()) shouldContain ReceiptsEmptyState
  }

  @Test
  fun `factory adds scan available if no blocking states`() {
    val receipts = listOf(
      DataFixtures.getReceiptUIModel().copy(state = ReceiptState.COMPLETED)
    )
    createReceiptsData(receipts) shouldContain ReceiptsScanAvailable
  }

  @Test
  fun `factory doesn't add scan available if there are blocking states`() {
    val receipts = listOf(
      DataFixtures.getReceiptUIModel().copy(state = ReceiptState.APPROVED)
    )
    createReceiptsData(receipts) shouldNotContain ReceiptsScanAvailable
  }

}
