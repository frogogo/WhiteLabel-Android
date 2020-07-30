package ru.poprobuy.poprobuy.extension.binding

import coil.api.load
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.data.model.ui.ReceiptUiModel
import ru.poprobuy.poprobuy.databinding.ViewReceiptFooterApprovedBinding
import ru.poprobuy.poprobuy.databinding.ViewReceiptFooterCompletedBinding
import ru.poprobuy.poprobuy.databinding.ViewReceiptFooterProcessingBinding
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import ru.poprobuy.poprobuy.extension.setVisible

fun ViewReceiptFooterApprovedBinding.setReceipt(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state == ReceiptState.APPROVED)

  // TODO: 23.07.2020 Real shop name
  textViewStateSubtitle.text = context.getString(R.string.receipt_state_approved_description, "ВкусВилл")
}

fun ViewReceiptFooterProcessingBinding.setReceipt(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state == ReceiptState.PROCESSING)
}

fun ViewReceiptFooterCompletedBinding.setReceipt(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state == ReceiptState.COMPLETED)

  textViewStateSubtitle.text = context.getString(R.string.receipt_state_completed_subtitle, "Чай черный Greenfield")
  imageViewProduct.load("https://cloud.egin.al/s/Df2qMJwnHcTkqtP/preview") { // TODO: 23.07.2020 Real url
    placeholder(R.drawable.ic_placeholder)
  }
}
