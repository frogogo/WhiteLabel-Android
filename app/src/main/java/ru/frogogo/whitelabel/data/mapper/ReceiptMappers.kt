package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.receipt.Receipt
import ru.frogogo.whitelabel.data.model.api.receipt.ReceiptRejectReason
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptRejectReasonUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.util.Constants

fun Receipt.toDomain(): ReceiptUiModel = ReceiptUiModel(
  id = id,
  number = number,
  state = state,
  date = timestamp,
  value = sum,
  shopName = Constants.SHOP_NAME,
  rejectReason = rejectReason?.toDomain()
)

fun List<Receipt>.toDomain(): List<ReceiptUiModel> =
  map { it.toDomain() }

fun ReceiptRejectReason.toDomain(): ReceiptRejectReasonUiModel = ReceiptRejectReasonUiModel(
  reason = reason,
  reasonText = reasonText
)
