package ru.poprobuy.poprobuy.ui.profile.receipts

import com.skydoves.whatif.whatIf
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.model.ui.profile.receipts.ReceiptsEmptyState
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptsScanAvailable
import ru.poprobuy.poprobuy.dictionary.ReceiptState

object ReceiptsDataFactory {

  /**
   * States in which user can't scan new receipts
   */
  private val LOCKING_STATES = listOf(ReceiptState.APPROVED, ReceiptState.PROCESSING)

  fun createReceiptsData(receipts: List<ReceiptUiModel>): List<RecyclerViewItem> {
    val list = mutableListOf<RecyclerViewItem>()

    // Header
    if (receipts.isNotEmpty()) {
      receipts.none { it.state in LOCKING_STATES }.whatIf {
        list += ReceiptsScanAvailable
      }
    }

    // Empty state
    if (receipts.isEmpty()) {
      list += ReceiptsEmptyState
    }

    // Receipts
    list += receipts

    return list
  }

}
