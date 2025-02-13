package ru.frogogo.whitelabel.util.moshi.adapter

import com.squareup.moshi.FromJson
import ru.frogogo.whitelabel.dictionary.ReceiptState
import java.util.Locale

object ReceiptStateJsonAdapter {

  @FromJson
  fun fromJson(state: String): ReceiptState =
    ReceiptState.valueOf(state.uppercase(Locale.ENGLISH))
}
