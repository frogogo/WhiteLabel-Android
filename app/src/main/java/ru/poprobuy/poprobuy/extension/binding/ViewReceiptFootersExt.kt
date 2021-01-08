package ru.poprobuy.poprobuy.extension.binding

import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import coil.load
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel
import ru.poprobuy.poprobuy.databinding.ViewReceiptFooterApprovedBinding
import ru.poprobuy.poprobuy.databinding.ViewReceiptFooterCompletedBinding
import ru.poprobuy.poprobuy.databinding.ViewReceiptFooterProcessingBinding
import ru.poprobuy.poprobuy.databinding.ViewReceiptFooterRejectedBinding
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import ru.poprobuy.poprobuy.extension.setVisible

fun ViewReceiptFooterApprovedBinding.bind(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state == ReceiptState.APPROVED)

  textViewStateSubtitle.text = context.getString(
    R.string.receipt_state_approved_description,
    receipt.distributionNetwork?.name.orEmpty()
  )
}

fun ViewReceiptFooterProcessingBinding.bind(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state == ReceiptState.PROCESSING)
}

fun ViewReceiptFooterCompletedBinding.bind(receipt: ReceiptUiModel) {
  root.setVisible(receipt.state == ReceiptState.COMPLETED)

  textViewStateSubtitle.text = context.getString(
    R.string.receipt_state_completed_subtitle,
    receipt.product?.name.orEmpty()
  )
  imageViewProduct.load(receipt.product?.imageUrl.orEmpty()) {
    placeholder(R.drawable.ic_placeholder)
    error(R.drawable.ic_placeholder)
  }
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
