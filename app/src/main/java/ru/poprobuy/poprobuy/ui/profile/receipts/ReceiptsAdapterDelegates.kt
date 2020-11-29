package ru.poprobuy.poprobuy.ui.profile.receipts

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.model.ui.profile.receipts.ReceiptsEmptyState
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptsScanAvailable
import ru.poprobuy.poprobuy.databinding.ItemReceiptBinding
import ru.poprobuy.poprobuy.databinding.ItemReceiptsEmptyStateBinding
import ru.poprobuy.poprobuy.databinding.ItemScanAvailableBinding
import ru.poprobuy.poprobuy.extension.binding.setReceiptState
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.toDateTime
import ru.poprobuy.poprobuy.util.PriceUtils

typealias OnReceiptClickAction = (ReceiptUiModel) -> Unit

object ReceiptsAdapterDelegates {

  fun receiptDelegate(
    clickAction: OnReceiptClickAction,
  ) = adapterDelegateViewBinding<ReceiptUiModel, RecyclerViewItem, ItemReceiptBinding>(
    viewBinding = { layoutInflater, root -> ItemReceiptBinding.inflate(layoutInflater, root, false) }
  ) {

    itemView.setOnSafeClickListener { clickAction(item) }

    bind {
      binding.apply {
        textViewReceiptNumber.text = context.getString(R.string.receipt_number, item.number)
        textViewReceiptValue.text = PriceUtils.formatPrice(item.value)
        textViewDate.text = item.date.toDateTime()
        viewLabel.setReceiptState(item.state)
      }
    }
  }

  fun receiptEmptyState() =
    adapterDelegateViewBinding<ReceiptsEmptyState, RecyclerViewItem, ItemReceiptsEmptyStateBinding>(
      viewBinding = { layoutInflater, root -> ItemReceiptsEmptyStateBinding.inflate(layoutInflater, root, false) }
    ) {}

  fun scanAvailableDelegate() =
    adapterDelegateViewBinding<ReceiptsScanAvailable, RecyclerViewItem, ItemScanAvailableBinding>(
      viewBinding = { layoutInflater, root -> ItemScanAvailableBinding.inflate(layoutInflater, root, false) }
    ) {

    }

}
