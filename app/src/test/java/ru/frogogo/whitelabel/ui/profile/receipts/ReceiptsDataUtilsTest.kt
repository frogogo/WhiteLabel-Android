package ru.frogogo.whitelabel.ui.profile.receipts

import org.amshove.kluent.shouldContain
import org.junit.jupiter.api.Test
import ru.frogogo.whitelabel.data.model.ui.profile.receipts.ReceiptsEmptyState
import ru.frogogo.whitelabel.ui.profile.receipts.ReceiptsDataUtils.createReceiptsData

class ReceiptsDataUtilsTest {

  @Test
  fun `createReceiptsData should show empty state when list is empty`() {
    createReceiptsData(listOf()) shouldContain ReceiptsEmptyState
  }
}
