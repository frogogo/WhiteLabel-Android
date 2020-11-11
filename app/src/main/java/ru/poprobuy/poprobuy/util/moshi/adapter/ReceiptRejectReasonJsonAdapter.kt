package ru.poprobuy.poprobuy.util.moshi.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import ru.poprobuy.poprobuy.dictionary.ReceiptRejectReason

class ReceiptRejectReasonJsonAdapter {

  @FromJson
  fun fromJson(reason: String): ReceiptRejectReason =
    ReceiptRejectReason.valueOfOrDefault(reason)

  @ToJson
  fun toJson(reason: ReceiptRejectReason): String = reason.errorReason

}
