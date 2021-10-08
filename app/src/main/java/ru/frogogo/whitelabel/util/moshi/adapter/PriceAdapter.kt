package ru.frogogo.whitelabel.util.moshi.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal

object PriceAdapter {

  //  @Price
  @FromJson
  fun fromJson(string: String): BigDecimal {
    return BigDecimal(string)
  }

  @ToJson
  fun toJson(price: BigDecimal): String {
    return price.toPlainString()
  }
}
