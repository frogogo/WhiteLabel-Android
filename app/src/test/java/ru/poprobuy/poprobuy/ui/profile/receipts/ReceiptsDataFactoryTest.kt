package ru.poprobuy.poprobuy.ui.profile.receipts

import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldContainAll
import org.amshove.kluent.shouldNotContain
import org.junit.Test
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.mapper.toUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipts.ReceiptsScanAvailable
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import ru.poprobuy.poprobuy.ui.profile.receipts.ReceiptsDataFactory.createReceiptsData

class ReceiptsDataFactoryTest {

  @Test
  fun `factory maps receipts`() {
    val receipts = listOf(DataFixtures.receipt, DataFixtures.receipt.copy(number = 2))
    createReceiptsData(receipts) shouldContainAll receipts.map { it.toUiModel() }
  }

  @Test
  fun `factory adds scan available to empty list`() {
    createReceiptsData(listOf()) shouldContain ReceiptsScanAvailable
  }

  @Test
  fun `factory adds scan available if no blocking states`() {
    val receipts = listOf(
      DataFixtures.receipt.copy(state = ReceiptState.COMPLETED)
    )
    createReceiptsData(receipts) shouldContain ReceiptsScanAvailable
  }

  @Test
  fun `factory doesn't add scan available if there are blocking states`() {
    val receipts = listOf(
      DataFixtures.receipt.copy(state = ReceiptState.APPROVED)
    )
    createReceiptsData(receipts) shouldNotContain ReceiptsScanAvailable
  }

}
