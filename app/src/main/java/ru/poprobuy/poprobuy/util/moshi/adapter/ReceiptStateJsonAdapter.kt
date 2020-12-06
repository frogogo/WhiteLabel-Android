package ru.poprobuy.poprobuy.util.moshi.adapter

import com.squareup.moshi.FromJson
import ru.poprobuy.poprobuy.dictionary.ReceiptState
import java.util.*

class ReceiptStateJsonAdapter {

  @FromJson
  fun fromJson(state: String): ReceiptState = ReceiptState.valueOf(state.toUpperCase(Locale.ENGLISH))

}
