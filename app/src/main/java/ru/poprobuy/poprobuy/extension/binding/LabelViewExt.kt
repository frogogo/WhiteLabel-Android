package ru.poprobuy.poprobuy.extension.binding

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingProductUiModel
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import ru.poprobuy.poprobuy.dictionary.ReceiptState.*
import ru.poprobuy.poprobuy.extension.setVisible
import ru.poprobuy.poprobuy.extension.to
import ru.poprobuy.poprobuy.view.LabelView

fun LabelView.setReceiptState(state: ReceiptState) {
  val (textRes, colorRes, drawableRes) = when (state) {
    PROCESSING -> R.string.receipt_item_state_processing to R.color.receipt_state_processing to R.drawable.ic_time_wait
    APPROVED -> R.string.receipt_item_state_approved to R.color.receipt_state_approved to R.drawable.ic_accept
    REJECTED -> R.string.receipt_item_state_rejected to R.color.receipt_state_rejected to R.drawable.ic_rejected
    COMPLETED -> R.string.receipt_item_state_completed to R.color.receipt_state_completed to R.drawable.ic_accept
  }

  setText(textRes)
  setLabelBackground(context.getColor(colorRes))
  setIcon(drawableRes)
}

fun LabelView.setProductState(product: VendingProductUiModel) {
  setVisible(!product.isActive())

  // Do not continue if label shouldn't be shown
  if (product.isActive()) return

  setText(product.state.getNameRes() ?: return)
  setLabelBackground(context.getColor(product.state.getColorRes() ?: return))
}
