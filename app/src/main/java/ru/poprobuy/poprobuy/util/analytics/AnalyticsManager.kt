package ru.poprobuy.poprobuy.util.analytics

import androidx.core.os.bundleOf
import com.github.ajalt.timberkt.d
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsManager(private val analytics: FirebaseAnalytics) {

  fun trackScreen(screenName: String, className: String) {
    d { "Screen tracked - $screenName, $className" }
    val params = bundleOf(
      FirebaseAnalytics.Param.SCREEN_CLASS to className,
      FirebaseAnalytics.Param.SCREEN_NAME to screenName
    )
    analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
  }

}
