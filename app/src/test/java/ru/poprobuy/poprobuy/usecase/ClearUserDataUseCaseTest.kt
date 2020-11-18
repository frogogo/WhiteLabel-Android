package ru.poprobuy.poprobuy.usecase

import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.poprobuy.poprobuy.data.preferences.UserPreferences

class ClearUserDataUseCaseTest {

  private lateinit var useCase: ClearUserDataUseCase

  private val userPreferences: UserPreferences = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    useCase = ClearUserDataUseCase(
      userPreferences = userPreferences
    )
  }

  @Test
  fun `useCase should clear data`() {
    useCase()

    verifySequence {
      userPreferences.clearUserData()
    }
  }

}
