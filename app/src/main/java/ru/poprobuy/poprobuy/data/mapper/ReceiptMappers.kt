package ru.poprobuy.poprobuy.data.mapper

import ru.poprobuy.poprobuy.data.model.api.receipt.Receipt
import ru.poprobuy.poprobuy.data.model.api.receipt.ReceiptProduct
import ru.poprobuy.poprobuy.data.model.api.receipt.ReceiptDistributionNetwork
import ru.poprobuy.poprobuy.data.model.api.receipt.ReceiptRejectReason
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptProductUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptDistributionNetworkUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptRejectReasonUiModel
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel

fun Receipt.toDomain(): ReceiptUiModel = ReceiptUiModel(
  id = id,
  number = number,
  state = state,
  date = timestamp,
  value = sum,
  distributionNetwork = promotion?.toDomain(),
  product = product?.toDomain(),
  rejectReason = rejectReason?.toDomain()
)

fun ReceiptRejectReason.toDomain(): ReceiptRejectReasonUiModel = ReceiptRejectReasonUiModel(
  reason = reason,
  reasonText = reasonText
)

fun ReceiptDistributionNetwork.toDomain(): ReceiptDistributionNetworkUiModel = ReceiptDistributionNetworkUiModel(
  name = name
)

fun ReceiptProduct.toDomain(): ReceiptProductUiModel = ReceiptProductUiModel(
  id = id,
  name = name,
  imageUrl = imageUrl
)
