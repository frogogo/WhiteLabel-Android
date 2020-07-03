package ru.poprobuy.poprobuy.data.model.ui

import ru.poprobuy.poprobuy.dictionary.ReceiptStatus
import java.util.*

data class ReceiptUiModel(
  val id: Int,
  val shopName: String?,
  val date: Date,
  val value: Int,
  val status: ReceiptStatus
)
