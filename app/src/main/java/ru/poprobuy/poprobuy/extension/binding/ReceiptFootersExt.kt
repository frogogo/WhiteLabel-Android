package ru.poprobuy.poprobuy.extension.binding

import coil.api.load
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.data.model.ui.ReceiptUiModel
import ru.poprobuy.poprobuy.databinding.ViewReceiptFooterAcceptedBinding
import ru.poprobuy.poprobuy.databinding.ViewReceiptFooterCheckBinding
import ru.poprobuy.poprobuy.databinding.ViewReceiptFooterCompletedBinding
import ru.poprobuy.poprobuy.dictionary.ReceiptStatus
import ru.poprobuy.poprobuy.extension.setVisible

fun ViewReceiptFooterAcceptedBinding.setReceipt(receipt: ReceiptUiModel) {
  root.setVisible(receipt.status == ReceiptStatus.ACCEPTED)

  // TODO: 23.07.2020 Real shop name
  textViewStatusSubtitle.text = context.getString(R.string.receipt_status_accepted_description, "ВкусВилл")
}

fun ViewReceiptFooterCheckBinding.setReceipt(receipt: ReceiptUiModel) {
  root.setVisible(receipt.status == ReceiptStatus.CHECK)
}

fun ViewReceiptFooterCompletedBinding.setReceipt(receipt: ReceiptUiModel) {
  root.setVisible(receipt.status == ReceiptStatus.COMPLETED)

  textViewStatusSubtitle.text = context.getString(R.string.receipt_status_completed_subtitle, "Чай черный Greenfield")
  imageViewProduct.load("https://cloud.egin.al/s/Df2qMJwnHcTkqtP/preview") { // TODO: 23.07.2020 Real url
    placeholder(R.drawable.ic_placeholder)
  }
}
