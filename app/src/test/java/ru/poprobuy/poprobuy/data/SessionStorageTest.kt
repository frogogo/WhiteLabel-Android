package ru.poprobuy.poprobuy.data

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ru.poprobuy.test.DataFixtures

internal class SessionStorageTest {

  @Test
  fun `storage should save and return sessionId`() {
    val storage = SessionStorage()

    storage.saveSessionId(DataFixtures.SESSION_ID)
    storage.getSessionId() shouldBeEqualTo DataFixtures.SESSION_ID
  }
}
