package ru.frogogo.whitelabel.ui.profile.receipts

import com.skydoves.whatif.whatIf
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.profile.receipts.ReceiptsEmptyState
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptsScanAvailable
import ru.frogogo.whitelabel.dictionary.ReceiptState
import ru.frogogo.whitelabel.ui.profile.receipts.details.ReceiptDetailsButtonState

object ReceiptsDataUtils {

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

  fun getReceiptDetailsButtonState(receipts: List<ReceiptUiModel>): ReceiptDetailsButtonState {
    val canCreateReceipt = receipts.firstOrNull()?.state in listOf(ReceiptState.REJECTED)
    val canTakeProduct = receipts.firstOrNull()?.state in listOf(ReceiptState.APPROVED)

    return ReceiptDetailsButtonState(
      canCreateReceipt = canCreateReceipt,
      canTakeProduct = canTakeProduct,
    )
  }
}
