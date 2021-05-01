package ru.frogogo.whitelabel.analytics

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SystemEventsTest {

  @Test
  fun `TokenRefreshWork should lowercase result name`() {
    val event = SystemEvents.TokenRefreshWork(SystemEvents.TokenRefreshWork.Result.REFRESHED)

    event.getEventParameters().getString(SystemEvents.TokenRefreshWork.PARAM_RESULT) shouldBeEqualTo "refreshed"
  }
}
