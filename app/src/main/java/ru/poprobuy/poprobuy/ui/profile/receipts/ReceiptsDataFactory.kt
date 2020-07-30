package ru.poprobuy.poprobuy.ui.profile.receipts

import com.skydoves.whatif.whatIf
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.mapper.toUiModel
import ru.poprobuy.poprobuy.data.model.api.receipt.Receipt
import ru.poprobuy.poprobuy.data.model.ui.receipts.ReceiptsScanAvailable
import ru.poprobuy.poprobuy.dictionary.ReceiptState

object ReceiptsDataFactory {

  /**
   * States in which user can't scan new receipts
   */
  private val LOCKING_STATES = listOf(ReceiptState.APPROVED, ReceiptState.PROCESSING)

  fun createReceiptsData(receipts: List<Receipt>): List<RecyclerViewItem> {
    val receiptsMapped = receipts.map { it.toUiModel() }
    val list = mutableListOf<RecyclerViewItem>()

    // Header
    receiptsMapped.none { it.state in LOCKING_STATES }.whatIf {
      list += ReceiptsScanAvailable
    }

    // Receipts
    list += receiptsMapped

    return list
  }

}
