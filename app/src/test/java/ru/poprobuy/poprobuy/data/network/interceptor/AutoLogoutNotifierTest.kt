package ru.poprobuy.poprobuy.data.network.interceptor

import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import ru.poprobuy.test.rule.InstantTaskExecutorExtension
import ru.poprobuy.poprobuy.analytics.SystemEvents
import ru.poprobuy.poprobuy.core.Event
import ru.poprobuy.test.mockkObserver
import ru.poprobuy.poprobuy.util.analytics.AnalyticsManager

class AutoLogoutNotifierTest {

  @Suppress("unused")
  @JvmField
  @RegisterExtension
  val instantExecutorExtension = InstantTaskExecutorExtension()

  private lateinit var sut: AutoLogoutNotifier

  private val analyticsManager: AnalyticsManager = mockk(relaxed = true)

  private val eventObserver = mockkObserver<Event<Unit>>()

  @BeforeEach
  fun startUp() {
    sut = AutoLogoutNotifier(
      analyticsManager = analyticsManager
    ).apply {
      logoutEvent.observeForever(eventObserver)
    }
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
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
