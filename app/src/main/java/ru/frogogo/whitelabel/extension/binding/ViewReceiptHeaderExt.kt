package ru.frogogo.whitelabel.extension.binding

import android.content.res.ColorStateList
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.databinding.ViewReceiptHeaderBinding
import ru.frogogo.whitelabel.dictionary.ReceiptState
import ru.frogogo.whitelabel.extension.setSize
import ru.frogogo.whitelabel.extension.setVisible
import ru.frogogo.whitelabel.extension.toDateTime
import ru.frogogo.whitelabel.extension.updateMargin
import ru.frogogo.whitelabel.util.PriceUtils

private val STATES_WITHOUT_SHOP_NAME = emptyList<ReceiptState>()

fun ViewReceiptHeaderBinding.bind(receipt: ReceiptUiModel) {
  // Header
  val headerColor = context.getColor(receipt.state.getColor())
  viewHeader.backgroundTintList = ColorStateList.valueOf(headerColor)
  // Id
  textViewReceiptNumber.text = context.getString(R.string.receipt_number, receipt.number)
  // Value
  textViewReceiptValue.text = PriceUtils.formatPrice(receipt.value)
  // Shop
  textViewShop.text = receipt.shopName
  textViewShop.setVisible(receipt.state !in STATES_WITHOUT_SHOP_NAME)
  // Date
  textViewDate.text = receipt.date.toDateTime()
  textViewDate.setTextAppearance(
    if (receipt.state in STATES_WITHOUT_SHOP_NAME) R.style.ReceiptDateText_Big else R.style.ReceiptDateText_Small,
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
