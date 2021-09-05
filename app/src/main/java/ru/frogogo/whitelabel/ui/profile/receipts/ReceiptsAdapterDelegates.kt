package ru.frogogo.whitelabel.ui.profile.receipts

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.profile.receipts.ReceiptsEmptyState
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.databinding.ItemReceiptBinding
import ru.frogogo.whitelabel.databinding.ItemReceiptsEmptyStateBinding
import ru.frogogo.whitelabel.extension.binding.setReceiptState
import ru.frogogo.whitelabel.extension.setSafeOnClickListener
import ru.frogogo.whitelabel.extension.toDateTime
import ru.frogogo.whitelabel.util.PriceUtils

typealias OnReceiptClickAction = (ReceiptUiModel) -> Unit

object ReceiptsAdapterDelegates {

  fun receiptDelegate(
    clickAction: OnReceiptClickAction,
  ) = adapterDelegateViewBinding<ReceiptUiModel, RecyclerViewItem, ItemReceiptBinding>(
    viewBinding = { layoutInflater, root -> ItemReceiptBinding.inflate(layoutInflater, root, false) },
  ) {
    itemView.setSafeOnClickListener { clickAction(item) }

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
      viewBinding = { layoutInflater, root -> ItemReceiptsEmptyStateBinding.inflate(layoutInflater, root, false) },
    ) {}
}
