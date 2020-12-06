package ru.poprobuy.poprobuy.util.analytics

import android.os.Bundle

open class AnalyticsEvent(
  val eventName: String,
) {

  init {
    require(
      eventName.isNotBlank() &&
          // Event names can be up to 40 characters long
          eventName.length <= EVENT_NAME_MAX_LENGTH &&
          // may only contain alphanumeric characters and underscores ("_")
          eventName.all { it.isLetterOrDigit() || it == '_' } &&
          // must start with an alphabetic character
          eventName.first().isLetter()
    ) {
      "Event name policy violated - \"$eventName\""
    }
  }

  open fun getEventParameters(): Bundle? = null

  companion object {
    // https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.Event
    const val EVENT_NAME_MAX_LENGTH = 40
  }

}
