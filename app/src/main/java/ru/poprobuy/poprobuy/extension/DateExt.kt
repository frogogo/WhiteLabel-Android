package ru.poprobuy.poprobuy.extension

import ru.poprobuy.poprobuy.util.Constants
import java.text.SimpleDateFormat
import java.util.*

private const val FORMAT_DATE_TIME = "dd.MM.yyyy - HH:mm"

fun Date.toDateTime(): String = format(FORMAT_DATE_TIME, this)

private fun format(pattern: String, date: Date) = SimpleDateFormat(
  pattern, Locale.forLanguageTag(Constants.LOCALE_DEFAULT)
).format(date)
