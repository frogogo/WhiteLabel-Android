package ru.poprobuy.poprobuy.extension

import com.github.ajalt.timberkt.e
import com.squareup.moshi.JsonAdapter

fun <T> JsonAdapter<T>.fromJsonOrNull(json: String?): T? {
  json ?: return null
  return runCatching { fromJson(json) }
    .onFailure { e(it) { "Exception while converting json to object" } }
    .getOrNull()
}
