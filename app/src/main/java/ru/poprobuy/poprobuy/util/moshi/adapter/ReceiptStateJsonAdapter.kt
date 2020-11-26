package ru.poprobuy.poprobuy.util.moshi.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import java.util.*

class ReceiptStateJsonAdapter {

  @ToJson
  fun toJson(state: ReceiptState): String = state.name.toLowerCase(Locale.ENGLISH)

  @FromJson
  fun fromJson(state: String): ReceiptState = ReceiptState.valueOf(state.toUpperCase(Locale.ENGLISH))

}
