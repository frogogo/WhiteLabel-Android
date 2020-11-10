package ru.poprobuy.poprobuy.extension

import android.text.InputFilter
import android.widget.EditText

/**
 * Executes provided action block on handling defined ime action
 */
inline fun EditText.onImeAction(
  imeAction: Int,
  crossinline action: (EditText) -> Unit,
) {
  setOnEditorActionListener { _, actionId, _ ->
    if (actionId == imeAction) {
      action.invoke(this)
      return@setOnEditorActionListener true
    }
    false
  }
}

/**
 * Adds [InputFilter.LengthFilter] with defined length to [EditText]
 */
fun EditText.setMaxLength(maxLength: Int?) {
  // Get current filters except LengthFilter
  val filtersList = filters
    .filterNot { it is InputFilter.LengthFilter }
    .toMutableList()

  maxLength?.let { length ->
    filtersList += InputFilter.LengthFilter(length)
  }

  filters = filtersList.toTypedArray()
}
