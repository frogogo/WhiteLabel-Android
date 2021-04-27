package ru.frogogo.whitelabel.extension

import com.redmadrobot.inputmask.helper.Mask
import com.redmadrobot.inputmask.model.CaretString

/**
 * @return string formatted with given mask
 */
fun String.formatWithMask(mask: String): String {
  val caretGravity = CaretString.CaretGravity.FORWARD(false)
  val caret = CaretString(this, length, caretGravity)

  return Mask.getOrCreate(mask, emptyList())
    .apply(caret)
    .formattedText
    .string
}

fun String.getUnformattedPhoneNumber(): String {
  val regex = Regex("[()\\- ]") // Replaces all characters in

  return this.replace(regex, "")
}
