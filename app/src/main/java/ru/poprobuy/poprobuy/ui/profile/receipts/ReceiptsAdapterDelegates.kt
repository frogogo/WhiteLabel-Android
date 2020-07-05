package ru.poprobuy.poprobuy.ui.profile.receipts

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.model.ui.ReceiptUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipts.ReceiptsScanAvailable
import ru.poprobuy.poprobuy.databinding.ItemReceiptBinding
import ru.poprobuy.poprobuy.databinding.ItemScanAvailableBinding
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setReceiptStatus
import ru.poprobuy.poprobuy.extension.toDateTime
import ru.poprobuy.poprobuy.util.PriceUtils

typealias OnReceiptClickAction = (ReceiptUiModel) -> Unit

object ReceiptsAdapterDelegates {

  fun receiptDelegate(
    clickAction: OnReceiptClickAction
  ) = adapterDelegateViewBinding<ReceiptUiModel, RecyclerViewItem, ItemReceiptBinding>(
    viewBinding = { layoutInflater, root -> ItemReceiptBinding.inflate(layoutInflater, root, false) }
  ) {

    itemView.setOnSafeClickListener { clickAction(item) }

    bind {
      binding.apply {
        textViewReceiptId.text = context.getString(R.string.receipt_id, item.id)
        textViewReceiptValue.text = PriceUtils.formatPrice(item.value)
        textViewDate.text = item.date.toDateTime()
        viewLabel.setReceiptStatus(item.status)
      }
    }
  }

  fun scanAvailableDelegate() =
    adapterDelegateViewBinding<ReceiptsScanAvailable, RecyclerViewItem, ItemScanAvailableBinding>(
      viewBinding = { layoutInflater, root -> ItemScanAvailableBinding.inflate(layoutInflater, root, false) }
    ) {

    }

}
