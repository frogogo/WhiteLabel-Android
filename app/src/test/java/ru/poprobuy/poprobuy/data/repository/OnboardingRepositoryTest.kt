package ru.poprobuy.poprobuy.data.repository

import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.data.preferences.UserPreferences

class OnboardingRepositoryTest {

  private lateinit var onboardingRepository: OnboardingRepository
  private val userPreferences: UserPreferences = mockk(relaxed = true)

  @Before
  fun startUp() {
    onboardingRepository = OnboardingRepository(userPreferences)
  }

  @Test
  fun `onboarding state is stored to shared preferences`() {
    onboardingRepository.setOnboardingCompleted()
    verify { userPreferences.onboardingCompleted = true }
  }

}
