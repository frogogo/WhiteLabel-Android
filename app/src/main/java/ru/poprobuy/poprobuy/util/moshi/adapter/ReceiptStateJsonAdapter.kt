package ru.poprobuy.poprobuy.util.moshi.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import java.util.*

class ReceiptStateJsonAdapter {

  @ToJson
  fun toJson(chatType: ReceiptState): String = chatType.name.toLowerCase(Locale.ENGLISH)

  @FromJson
  fun fromJson(chatType: String): ReceiptState = ReceiptState.valueOf(chatType.toUpperCase(Locale.ENGLISH))

}
