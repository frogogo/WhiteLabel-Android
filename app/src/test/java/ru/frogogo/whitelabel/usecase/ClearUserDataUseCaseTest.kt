package ru.frogogo.whitelabel.usecase

import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.frogogo.whitelabel.data.preferences.UserPreferences

class ClearUserDataUseCaseTest {

  private lateinit var useCase: ClearUserDataUseCase

  private val userPreferences: UserPreferences = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    useCase = ClearUserDataUseCase(
      userPreferences = userPreferences
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `useCase should clear data`() {
    useCase()

    verifySequence {
      userPreferences.clearUserData()
    }
  }
}
