package ru.poprobuy.poprobuy.extension

import android.widget.TextView
import androidx.annotation.StringRes
import com.skydoves.whatif.whatIfNotNull

fun TextView.setNullableTextRes(@StringRes resId: Int?) {
  resId.whatIfNotNull(
    // Set passed resource if it is not null
    whatIf = { setText(it) },
    // Clear text
    whatIfNot = { text = "" }
  )
}
