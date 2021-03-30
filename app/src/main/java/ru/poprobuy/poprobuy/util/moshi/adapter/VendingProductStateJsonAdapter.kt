package ru.poprobuy.poprobuy.util.moshi.adapter

import com.squareup.moshi.FromJson
import ru.poprobuy.poprobuy.dictionary.VendingProductState
import java.util.*

class VendingProductStateJsonAdapter {

  @FromJson
  fun fromJson(state: String): VendingProductState =
    VendingProductState.valueOf(state.toUpperCase(Locale.ENGLISH))
}
