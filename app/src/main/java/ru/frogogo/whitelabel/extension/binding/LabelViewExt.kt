package ru.frogogo.whitelabel.extension.binding

import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.dictionary.ReceiptState
import ru.frogogo.whitelabel.dictionary.ReceiptState.*
import ru.frogogo.whitelabel.extension.to
import ru.frogogo.whitelabel.view.LabelView

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
