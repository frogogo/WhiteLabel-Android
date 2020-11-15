package ru.poprobuy.poprobuy.util.analytics

import org.junit.jupiter.api.*

internal class AnalyticsEventTest {

  @Test
  fun `event name shouldn't be blank`() {
    assertThrows<Exception> {
      AnalyticsEvent("")
    }
  }

  @Test
  fun `event name shouldn't exceed max length`() {
    assertThrows<Exception> {
      AnalyticsEvent("a".repeat(AnalyticsEvent.EVENT_NAME_MAX_LENGTH + 1))
    }
  }

  @Test
  fun `event name shouldn't contain not alphanumeric characters`() {
    assertThrows<Exception> {
      AnalyticsEvent("event_name!@#$*(&")
    }
  }

  @Test
  fun `event name should start with alphabetic char`() {
    assertThrows<Exception> {
      AnalyticsEvent("_event")
    }
  }

  @Test
  fun `event name should be verified for al rules`() {
    assertThrows<Exception> {
      AnalyticsEvent("_event")
    }
  }

  @TestFactory
  fun `proper event name should be accepted`(): List<DynamicTest> {
    val names = listOf(
      "event",
      "event_name",
      "event_name_",
      "event_1",
      "event_1_",
    )

    fun test(name: String) {
      assertDoesNotThrow {
        AnalyticsEvent(name)
      }
    }

    return names.mapIndexed { index, param ->
      DynamicTest.dynamicTest("Event name should be accepted $index") {
        test(param)
      }
    }
  }

}
