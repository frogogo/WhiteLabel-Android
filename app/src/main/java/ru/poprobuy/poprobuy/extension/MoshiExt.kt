package ru.poprobuy.poprobuy.extension

import com.github.ajalt.timberkt.e
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

fun <T> JsonAdapter<T>.fromJsonOrNull(json: String?): T? {
  json ?: return null
  return runCatching { fromJson(json) }
    .onFailure { e(it) { "Exception while converting json to object" } }
    .getOrNull()
}

inline fun <reified T : Any> Moshi.fromJson(
  json: String,
  lenient: Boolean = false,
): T? {
  var jsonAdapter = adapter(T::class.java)
  if (lenient) {
    jsonAdapter = jsonAdapter.lenient()
  }
  return jsonAdapter.fromJson(json)
}
