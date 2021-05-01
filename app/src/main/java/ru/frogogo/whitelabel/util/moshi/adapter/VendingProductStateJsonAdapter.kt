package ru.frogogo.whitelabel.util.moshi.adapter

import com.squareup.moshi.FromJson
import ru.frogogo.whitelabel.dictionary.VendingProductState
import java.util.*

class VendingProductStateJsonAdapter {

  @FromJson
  fun fromJson(state: String): VendingProductState =
    VendingProductState.valueOf(state.toUpperCase(Locale.ENGLISH))
}
