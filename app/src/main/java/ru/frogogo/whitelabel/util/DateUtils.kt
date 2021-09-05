package ru.frogogo.whitelabel.util

import java.util.Date
import kotlin.math.roundToInt

private const val MILLIS_IN_SECOND = 1000F

object DateUtils {

  /**
   * @return difference between provided dates in seconds
   */
  fun calculateSecondsDifferenceBetweenDates(start: Date, end: Date): Int {
    val difference = (end.time - start.time) / MILLIS_IN_SECOND
    return difference.roundToInt()
  }
}
