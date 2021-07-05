package ru.frogogo.whitelabel.extension.binding

import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.databinding.ViewReceiptFooterApprovedBinding
import ru.frogogo.whitelabel.databinding.ViewReceiptFooterProcessingBinding
import ru.frogogo.whitelabel.databinding.ViewReceiptFooterRejectedBinding
import ru.frogogo.whitelabel.dictionary.ReceiptState
import ru.frogogo.whitelabel.extension.setVisible

fun ViewReceiptFooterApprovedBinding.bind(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state == ReceiptState.APPROVED)

  textViewStateSubtitle.text = context.getString(R.string.receipt_state_approved_description)
}

fun ViewReceiptFooterProcessingBinding.bind(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state == ReceiptState.PROCESSING)
}

fun ViewReceiptFooterRejectedBinding.bind(receipt: ReceiptUiModel, showTopDivider: Boolean = false) {
  root.setVisible(receipt.state == ReceiptState.REJECTED)

  divider1.setVisible(showTopDivider)
  textViewRejectReason.text = receipt.rejectReason?.reasonText
}
