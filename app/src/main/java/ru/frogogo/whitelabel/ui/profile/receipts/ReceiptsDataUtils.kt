package ru.frogogo.whitelabel.ui.profile.receipts

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.profile.receipts.ReceiptsEmptyState
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel

object ReceiptsDataUtils {

  fun createReceiptsData(receipts: List<ReceiptUiModel>): List<RecyclerViewItem> {
    val list = mutableListOf<RecyclerViewItem>()

    // Empty state
    receipts.ifEmpty {
      list += ReceiptsEmptyState
    }

    // Receipts
    list += receipts

    return list
  }
}
