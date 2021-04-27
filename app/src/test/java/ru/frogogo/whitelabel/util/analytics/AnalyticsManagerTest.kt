package ru.frogogo.whitelabel.util.analytics

import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class AnalyticsManagerTest {

  private lateinit var manager: AnalyticsManager

  private val firebaseAnalytics: FirebaseAnalytics = mockk(relaxed = true)

  @Before
  fun startUp() {
    manager = AnalyticsManager(
      analytics = firebaseAnalytics,
    )
  }

  @Test
  fun `manager should log event`() {
    val params = bundleOf("name" to 1)
    val event = object : AnalyticsEvent("event") {
      override fun getEventParameters(): Bundle? = params
    }

    manager.logEvent(event)

    verifySequence {
      firebaseAnalytics.logEvent("event", params)
    }
  }

  @Test
  fun `manager should log event without params`() {
    val event = AnalyticsEvent("event")

    manager.logEvent(event)

    verifySequence {
      firebaseAnalytics.logEvent("event", null)
    }
  }

  @Test
  fun `manager should track screen view`() {
    val screenName = "screen_name"
    val className = "class_name"
    manager.trackScreen(screenName, className)

    verifySequence {
      firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, match { bundle ->
        bundle.getString(FirebaseAnalytics.Param.SCREEN_CLASS) == className &&
            bundle.getString(FirebaseAnalytics.Param.SCREEN_NAME) == screenName
      })
    }
  }
}
