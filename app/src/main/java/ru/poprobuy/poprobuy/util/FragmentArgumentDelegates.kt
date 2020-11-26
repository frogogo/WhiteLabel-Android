package ru.poprobuy.poprobuy.util

import androidx.fragment.app.Fragment

inline fun <reified T> Fragment.argument(name: String): Lazy<T> = lazy {
  val arguments = arguments
  requireNotNull(arguments) { "arguments must be supplied" }
  arguments.get(name) as T
}
