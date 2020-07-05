package ru.poprobuy.poprobuy.extension

import ru.poprobuy.poprobuy.R
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
