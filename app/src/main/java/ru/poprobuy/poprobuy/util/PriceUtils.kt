package ru.poprobuy.poprobuy.util

import java.text.NumberFormat
import java.util.*
import kotlin.math.absoluteValue

object PriceUtils {

  private val CURRENCY_SYMBOL = mapOf(
    AppLocale.RU to "\u20BD"
  )

  /**
   * Returns formatter price with currency symbol
   * @param price integer price value
   * @param formatSign whether include sign or not
   */
  fun formatPrice(price: Int, formatSign: Boolean = false) =
    "${price.format(formatSign)} ${CURRENCY_SYMBOL[AppLocale.RU]}"

  private fun Int.format(withSign: Boolean): String {
    val numberString = NumberFormat.getInstance(Locale.FRENCH).format(this.absoluteValue)
    if (!withSign) return numberString
    val sign = if (this >= 0) '+' else '−'
    return "$sign $numberString"
  }

  private sealed class AppLocale {
    object RU : AppLocale()
  }

}
