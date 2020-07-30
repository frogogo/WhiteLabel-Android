package ru.poprobuy.poprobuy.extension.binding

import android.content.res.ColorStateList
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.data.model.ui.ReceiptUiModel
import ru.poprobuy.poprobuy.databinding.ViewReceiptHeaderBinding
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import ru.poprobuy.poprobuy.extension.setSize
import ru.poprobuy.poprobuy.extension.setVisible
import ru.poprobuy.poprobuy.extension.toDateTime
import ru.poprobuy.poprobuy.extension.updateMargin
import ru.poprobuy.poprobuy.util.PriceUtils

fun ViewReceiptHeaderBinding.setReceipt(receipt: ReceiptUiModel) {
  // Header
  val headerColor = context.getColor(receipt.state.getColor())
  viewHeader.backgroundTintList = ColorStateList.valueOf(headerColor)
  // Id
  textViewReceiptNumber.text = context.getString(R.string.receipt_number, receipt.number)
  // Value
  textViewReceiptValue.text = PriceUtils.formatPrice(receipt.value)
  // Shop
  textViewShop.text = receipt.shopName
  textViewShop.setVisible(receipt.state != ReceiptState.PROCESSING)
  // Date
  textViewDate.text = receipt.date.toDateTime()
  textViewDate.setTextAppearance(
    if (receipt.state == ReceiptState.PROCESSING) R.style.ReceiptDateText_Big else R.style.ReceiptDateText_Small
  )
  // Status Icon
  imageViewState.setImageResource(receipt.state.getHeaderIcon())
  // Status Name
  textViewState.text = context.getString(receipt.state.getName())
}

fun ViewReceiptHeaderBinding.useLargeSize() {
  val height = context.resources.getDimensionPixelSize(R.dimen.receipt_header_height_large)
  viewHeader.setSize(newHeight = height)

  val marginState = context.resources.getDimensionPixelSize(R.dimen.receipt_header_state_margin_top_large)
  textViewState.updateMargin(top = marginState)

  val marginTop = context.resources.getDimensionPixelSize(R.dimen.receipt_header_margin_top_large)
  textViewReceiptNumber.updateMargin(top = marginTop)
  textViewReceiptValue.updateMargin(top = marginTop)
}
