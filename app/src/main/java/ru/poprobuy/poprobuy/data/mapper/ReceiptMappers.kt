package ru.poprobuy.poprobuy.data.mapper

import ru.poprobuy.poprobuy.data.model.api.receipt.Receipt
import ru.poprobuy.poprobuy.data.model.api.receipt.ReceiptProduct
import ru.poprobuy.poprobuy.data.model.api.receipt.ReceiptPromotion
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptProductUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptPromotionUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel

fun Receipt.toUiModel(): ReceiptUiModel = ReceiptUiModel(
  id = id,
  number = number,
  state = state,
  date = timestamp,
  value = sum,
  promotion = promotion?.toDomain(),
  product = product?.toDomain(),
  rejectReason = rejectReason
)

fun ReceiptPromotion.toDomain(): ReceiptPromotionUiModel = ReceiptPromotionUiModel(
  name = name
)

fun ReceiptProduct.toDomain(): ReceiptProductUiModel = ReceiptProductUiModel(
  id = id,
  name = name,
  imageUrl = imageUrl
)
