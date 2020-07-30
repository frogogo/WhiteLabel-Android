package ru.poprobuy.poprobuy.util.moshi

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import ru.poprobuy.poprobuy.util.moshi.adapter.ReceiptStateJsonAdapter
import java.util.*

object MoshiUtils {

  fun getNetworkAdapter(): Moshi = Moshi.Builder()
    .add(Date::class.java, Rfc3339DateJsonAdapter())
    .add(ReceiptStateJsonAdapter())
    .build()

}
