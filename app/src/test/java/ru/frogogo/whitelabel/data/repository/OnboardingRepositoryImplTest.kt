package ru.frogogo.whitelabel.data.repository

import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldHaveSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.frogogo.test.base.RepositoryTest
import ru.frogogo.whitelabel.data.preferences.UserPreferences

@ExperimentalCoroutinesApi
class OnboardingRepositoryImplTest : RepositoryTest() {

  private lateinit var repository: OnboardingRepository

  private val userPreferences: UserPreferences = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    repository = OnboardingRepositoryImpl(
      dispatchers = coroutineTestExtension.testDispatcherProvider,
      userPreferences = userPreferences
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `repository should return onboarding pages`() {
    val pages = repository.getPages()

    pages shouldHaveSize 3
    confirmVerified()
  }

  @Test
  fun `repository should save onboarding state`() {
    repository.setOnboardingCompleted()

    verify {
      userPreferences.onboardingCompleted = true
    }
    confirmVerified()
  }

  private fun confirmVerified() {
    confirmVerified(userPreferences)
  }
}
