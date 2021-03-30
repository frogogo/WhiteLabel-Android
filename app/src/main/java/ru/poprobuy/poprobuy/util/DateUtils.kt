package ru.poprobuy.poprobuy.util

import java.util.*
import kotlin.math.roundToInt

object DateUtils {

  /**
   * @return difference between provided dates in seconds
   */
  fun calculateSecondsDifferenceBetweenDates(start: Date, end: Date): Int {
    val difference = (end.time - start.time) / 1000F
    return difference.roundToInt()
  }
}
