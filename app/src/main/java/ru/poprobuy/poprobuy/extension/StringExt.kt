package ru.poprobuy.poprobuy.extension

import com.redmadrobot.inputmask.helper.Mask
import com.redmadrobot.inputmask.model.CaretString

/**
 * @return string formatted with given mask
 */
fun String.formatWithMask(mask: String): String {
  val caretGravity = CaretString.CaretGravity.FORWARD(true)
  val caret = CaretString(this, 0, caretGravity)

  return Mask.getOrCreate(mask, emptyList())
    .apply(caret)
    .formattedText
    .string
}
