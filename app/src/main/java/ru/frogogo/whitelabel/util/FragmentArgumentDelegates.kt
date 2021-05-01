package ru.frogogo.whitelabel.util

import androidx.fragment.app.Fragment

inline fun <reified T> Fragment.argument(name: String): Lazy<T> = lazy {
  requireArguments().get(name) as T
}
