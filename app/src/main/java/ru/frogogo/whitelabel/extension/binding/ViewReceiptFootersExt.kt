package ru.frogogo.whitelabel.extension.binding

import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.databinding.ViewReceiptFooterApprovedBinding
import ru.frogogo.whitelabel.databinding.ViewReceiptFooterCompletedBinding
import ru.frogogo.whitelabel.databinding.ViewReceiptFooterProcessingBinding
import ru.frogogo.whitelabel.databinding.ViewReceiptFooterRejectedBinding
import ru.frogogo.whitelabel.dictionary.ReceiptState
import ru.frogogo.whitelabel.extension.setVisible

fun ViewReceiptFooterApprovedBinding.bind(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state == ReceiptState.APPROVED)

  // textViewStateSubtitle.text = context.getString(
  //  R.string.receipt_state_approved_description,
  //  receipt.distributionNetwork?.name.orEmpty()
  // )
}

fun ViewReceiptFooterProcessingBinding.bind(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state == ReceiptState.PROCESSING)
}

fun ViewReceiptFooterCompletedBinding.bind(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state == ReceiptState.COMPLETED)

  // textViewStateSubtitle.text = context.getString(
  //  R.string.receipt_state_completed_subtitle,
  //  receipt.product?.name.orEmpty()
  // )
  // imageViewProduct.load(receipt.product?.imageUrl.orEmpty()) {
  //  placeholder(R.drawable.ic_placeholder)
  //  error(R.drawable.ic_placeholder)
  // }
}

fun ViewReceiptFooterRejectedBinding.bind(receipt: ReceiptUiModel, showTopDivider: Boolean = false) {
  root.setVisible(receipt.state == ReceiptState.REJECTED)

  divider1.setVisible(showTopDivider)

  textViewRejectReason.text = buildSpannedString {
    val colorSpan = ForegroundColorSpan(context.getColor(R.color.receipt_error_reason))
    val appearanceSpan = TextAppearanceSpan(context, R.style.Text_Subhead_Medium)
    inSpans(colorSpan, appearanceSpan) {
      append(context.getString(R.string.receipt_state_rejected_reason))
    }

    append(" ")
    receipt.rejectReason?.reasonText?.let { append(it) }
  }
}
