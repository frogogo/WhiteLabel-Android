package ru.frogogo.whitelabel.data.mapper

import ru.frogogo.whitelabel.data.model.api.receipt.Receipt
import ru.frogogo.whitelabel.data.model.api.receipt.ReceiptDistributionNetwork
import ru.frogogo.whitelabel.data.model.api.receipt.ReceiptProduct
import ru.frogogo.whitelabel.data.model.api.receipt.ReceiptRejectReason
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptDistributionNetworkUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptProductUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptRejectReasonUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel

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
