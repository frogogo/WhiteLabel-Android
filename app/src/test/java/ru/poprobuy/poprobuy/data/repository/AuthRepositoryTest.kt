package ru.poprobuy.poprobuy.data.repository

import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.data.preferences.UserPreferences

class AuthRepositoryTest {

  private lateinit var authRepository: AuthRepository
  private val poprobuyApi: PoprobuyApi = mockk(relaxed = true)
  private val userPreferences: UserPreferences = mockk(relaxed = true)

  @Before
  fun startUp() {
    authRepository = AuthRepository(poprobuyApi, userPreferences)
  }

  @Test
  fun `policy state is stored to shared preferences`() {
    authRepository.setPolicyAccepted()
    verify { userPreferences.policyAccepted = true }
  }

  @Test
  fun `auth token is stored to shared preferences`() {
    authRepository.saveAuthToken(DataFixtures.ACCESS_TOKEN)
    verify { userPreferences.accessToken = DataFixtures.ACCESS_TOKEN }
  }

  @Test
  fun `authorization state is stored to shared preferences`() {
    authRepository.setUserAuthorized()
    verify { userPreferences.isLoggedIn = true }
  }

  @Test
  fun `data is cleared on logout`() {
    authRepository.logout()
    verify { userPreferences.clearData() }
  }

}
