package ru.frogogo.whitelabel.extension

import ru.frogogo.whitelabel.util.Constants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FORMAT_DATE_TIME = "dd.MM.yyyy - HH:mm"
private const val FORMAT_SIMPLE_DATE = "dd.MM.yyyy"

/**
 * @sample 10.12.2020 - 23:55
 */
fun Date.toDateTime(): String = format(FORMAT_DATE_TIME, this)

/**
 * @sample 10.12.2020
 */
fun Date.toSimpleDate(): String = format(FORMAT_SIMPLE_DATE, this)

private fun format(pattern: String, date: Date): String = SimpleDateFormat(
  pattern, Locale.forLanguageTag(Constants.LOCALE_DEFAULT),
).format(date)
