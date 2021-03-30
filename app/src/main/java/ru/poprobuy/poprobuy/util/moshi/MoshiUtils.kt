package ru.poprobuy.poprobuy.util.moshi

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import ru.poprobuy.poprobuy.util.moshi.adapter.AuthenticationResponseAdapter
import ru.poprobuy.poprobuy.util.moshi.adapter.ReceiptStateJsonAdapter
import ru.poprobuy.poprobuy.util.moshi.adapter.VendingProductStateJsonAdapter
import java.util.*

object MoshiUtils {

  val networkErrorParserMoshi: Moshi = Moshi.Builder()
    .build()

  fun getNetworkAdapter(): Moshi = Moshi.Builder()
    .add(Date::class.java, Rfc3339DateJsonAdapter())
    .add(ReceiptStateJsonAdapter())
    .add(AuthenticationResponseAdapter())
    .add(VendingProductStateJsonAdapter())
    .build()
}
