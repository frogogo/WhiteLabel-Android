package ru.poprobuy.poprobuy.data.repository

import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.data.preferences.UserPreferences

class AuthRepositoryTest {

  private lateinit var authRepository: AuthRepository
  private val userPreferences: UserPreferences = mockk(relaxed = true)

  @Before
  fun startUp() {
    authRepository = AuthRepository(userPreferences)
  }

  @Test
  fun `onboarding state is stored to shared preferences`() {
    authRepository.setPolicyAccepted()
    verify { userPreferences.policyAccepted = true }
  }

}
