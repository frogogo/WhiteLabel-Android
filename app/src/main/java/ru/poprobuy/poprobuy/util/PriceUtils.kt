package ru.poprobuy.poprobuy.util

import java.text.NumberFormat
import java.util.*
import kotlin.math.absoluteValue

private val CURRENCY_SYMBOL by lazy {
  mapOf(AppLocale.RU to "\u20BD")
}

private val numberFormat: NumberFormat by lazy {
  NumberFormat.getInstance(Locale.FRENCH)
}

private fun Int.format(withSign: Boolean): String {
  val numberString = numberFormat.format(this.absoluteValue)
  if (!withSign) return numberString
  val sign = if (this >= 0) '+' else '−'
  return "$sign $numberString"
}

object PriceUtils {

  /**
   * Returns formatter price with currency symbol
   * @param price integer price value
   * @param formatSign whether include sign or not
   */
  fun formatPrice(price: Int, formatSign: Boolean = false): String =
    "${price.format(formatSign)} ${CURRENCY_SYMBOL[AppLocale.RU]}"
}

private sealed class AppLocale {
  object RU : AppLocale()
}
