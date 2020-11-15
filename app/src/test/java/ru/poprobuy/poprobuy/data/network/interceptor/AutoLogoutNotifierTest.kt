package ru.poprobuy.poprobuy.data.network.interceptor

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.poprobuy.poprobuy.analytics.SystemEvents
import ru.poprobuy.poprobuy.mockkObserver
import ru.poprobuy.poprobuy.util.Event
import ru.poprobuy.poprobuy.util.analytics.AnalyticsManager

class AutoLogoutNotifierTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var sut: AutoLogoutNotifier

  private val analyticsManager: AnalyticsManager = mockk(relaxed = true)

  private val eventObserver = mockkObserver<Event<Unit>>()

  @Before
  fun startUp() {
    sut = AutoLogoutNotifier(
      analyticsManager = analyticsManager
    ).apply {
      logoutEvent.observeForever(eventObserver)
    }
  }

  @Test
  fun `logout should send event`() {
    sut.logout()

    verifySequence {
      analyticsManager.logEvent(SystemEvents.AutoLogout)
      eventObserver.onChanged(Event(Unit))
    }
    confirmVerified()
  }

  private fun confirmVerified() {
    confirmVerified(analyticsManager, eventObserver)
  }

}
