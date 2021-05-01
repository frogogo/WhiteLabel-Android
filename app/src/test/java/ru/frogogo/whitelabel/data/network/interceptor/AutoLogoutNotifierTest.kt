package ru.frogogo.whitelabel.data.network.interceptor

import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import ru.frogogo.test.rule.InstantTaskExecutorExtension
import ru.frogogo.whitelabel.analytics.SystemEvents
import ru.frogogo.whitelabel.core.Event
import ru.frogogo.test.mockkObserver
import ru.frogogo.whitelabel.util.analytics.AnalyticsManager

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
