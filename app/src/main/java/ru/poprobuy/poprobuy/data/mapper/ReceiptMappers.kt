package ru.poprobuy.poprobuy.data.mapper

import ru.poprobuy.poprobuy.data.model.api.receipt.Receipt
import ru.poprobuy.poprobuy.data.model.ui.ReceiptUiModel

fun Receipt.toUiModel(): ReceiptUiModel = ReceiptUiModel(
  id = id,
  number = number,
  state = state,
  date = timestamp,
  value = sum,
  shopName = null
)
