package ru.poprobuy.poprobuy.extension.binding

import android.content.res.ColorStateList
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.data.model.ui.ReceiptUiModel
import ru.poprobuy.poprobuy.databinding.ViewReceiptHeaderBinding
import ru.poprobuy.poprobuy.dictionary.ReceiptStatus
import ru.poprobuy.poprobuy.extension.setSize
import ru.poprobuy.poprobuy.extension.setVisible
import ru.poprobuy.poprobuy.extension.toDateTime
import ru.poprobuy.poprobuy.extension.updateMargin
import ru.poprobuy.poprobuy.util.PriceUtils

fun ViewReceiptHeaderBinding.setReceipt(receipt: ReceiptUiModel) {
  // Header
  val headerColor = context.getColor(receipt.status.getColor())
  viewHeader.backgroundTintList = ColorStateList.valueOf(headerColor)
  // Id
  textViewReceiptId.text = context.getString(R.string.receipt_id, receipt.id)
  // Value
  textViewReceiptValue.text = PriceUtils.formatPrice(receipt.value)
  // Shop
  textViewShop.text = receipt.shopName
  textViewShop.setVisible(receipt.status != ReceiptStatus.CHECK)
  // Date
  textViewDate.text = receipt.date.toDateTime()
  textViewDate.setTextAppearance(
    if (receipt.status == ReceiptStatus.CHECK) R.style.ReceiptDateText_Big else R.style.ReceiptDateText_Small
  )
  // Status Icon
  imageViewStatus.setImageResource(receipt.status.getHeaderIcon())
  // Status Name
  textViewStatus.text = context.getString(receipt.status.getName())
}

fun ViewReceiptHeaderBinding.useLargeSize() {
  val height = context.resources.getDimensionPixelSize(R.dimen.receipt_header_height_large)
  viewHeader.setSize(newHeight = height)

  val marginStatus = context.resources.getDimensionPixelSize(R.dimen.receipt_header_status_margin_top_large)
  textViewStatus.updateMargin(top = marginStatus)

  val marginTop = context.resources.getDimensionPixelSize(R.dimen.receipt_header_margin_top_large)
  textViewReceiptId.updateMargin(top = marginTop)
  textViewReceiptValue.updateMargin(top = marginTop)
}
