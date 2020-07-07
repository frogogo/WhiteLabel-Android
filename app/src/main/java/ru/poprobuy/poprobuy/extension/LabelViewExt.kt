package ru.poprobuy.poprobuy.extension

import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.data.model.ui.product.ProductUiModel
import ru.poprobuy.poprobuy.dictionary.ReceiptStatus
import ru.poprobuy.poprobuy.dictionary.ReceiptStatus.*
import ru.poprobuy.poprobuy.view.LabelView

fun LabelView.setReceiptStatus(status: ReceiptStatus) {
  val (textRes, colorRes, drawableRes) = when (status) {
    ACCEPTED -> R.string.receipt_item_status_accepted to R.color.receipt_status_accept to R.drawable.ic_accept
    REJECTED -> R.string.receipt_item_status_rejected to R.color.receipt_status_reject to R.drawable.ic_rejected
    CHECK -> R.string.receipt_item_status_check to R.color.receipt_status_check to R.drawable.ic_time_wait
  }

  setText(textRes)
  setLabelBackground(context.getColor(colorRes))
  setIcon(drawableRes)
}

fun LabelView.setProductState(product: ProductUiModel) {
  setVisible(!product.isActive())

  // Do not continue if label shouldn't be shown
  if (product.isActive()) return

  val (textRes, colorRes) = when {
    product.triedBefore -> R.string.products_status_tried_before to R.color.products_state_tried_before
    !product.inStock -> R.string.products_status_out_of_stock to R.color.products_state_out_of_stock
    else -> 1 to 1 // Should never happens
  }

  setText(textRes)
  setLabelBackground(context.getColor(colorRes))
}
