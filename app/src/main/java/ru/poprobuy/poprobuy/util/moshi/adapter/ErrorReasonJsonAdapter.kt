package ru.poprobuy.poprobuy.util.moshi.adapter

import androidx.annotation.StringRes
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import ru.poprobuy.poprobuy.dictionary.ErrorReason
import ru.poprobuy.poprobuy.util.moshi.qualifier.ErrorReasonQualifier
import java.util.*

class ErrorReasonJsonAdapter {

  @FromJson
  @StringRes
  @ErrorReasonQualifier
  fun fromJson(error: String): Int = ErrorReason.valueOfOrDefault(error).errorResId

  @ToJson
  fun toJson(@ErrorReasonQualifier errorReasonResId: Int): String {
    val errorReason = ErrorReason
      .values()
      .find { it.errorResId == errorReasonResId }
      ?: ErrorReason.DEFAULT

    return errorReason.errorReason.toLowerCase(Locale.ENGLISH)
  }

}
