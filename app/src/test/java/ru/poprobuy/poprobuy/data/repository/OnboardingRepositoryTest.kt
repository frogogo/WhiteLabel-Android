package ru.poprobuy.poprobuy.data.repository

import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldHaveSize
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.data.preferences.UserPreferences

class OnboardingRepositoryTest {

  private lateinit var repository: OnboardingRepository

  private val userPreferences: UserPreferences = mockk(relaxed = true)

  @Before
  fun startUp() {
    repository = OnboardingRepository(
      userPreferences = userPreferences
    )
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
