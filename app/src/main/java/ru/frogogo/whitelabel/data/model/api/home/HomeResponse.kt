package ru.frogogo.whitelabel.data.model.api.home

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.frogogo.whitelabel.data.model.api.receipt.Receipt

@JsonClass(generateAdapter = true)
class HomeResponse(
  @Json(name = "receipt")
  val receipt: Receipt?,
)
