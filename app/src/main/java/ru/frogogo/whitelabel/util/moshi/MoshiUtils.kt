package ru.frogogo.whitelabel.util.moshi

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import ru.frogogo.whitelabel.util.moshi.adapter.AuthenticationResponseAdapter
import ru.frogogo.whitelabel.util.moshi.adapter.PriceAdapter
import ru.frogogo.whitelabel.util.moshi.adapter.ReceiptStateJsonAdapter
import java.util.Date

object MoshiUtils {

  val networkErrorParserMoshi: Moshi = Moshi.Builder()
    .build()

  fun getNetworkAdapter(): Moshi = Moshi.Builder()
    .add(Date::class.java, Rfc3339DateJsonAdapter())
    .add(ReceiptStateJsonAdapter)
    .add(AuthenticationResponseAdapter)
    .add(PriceAdapter)
    .build()
}
