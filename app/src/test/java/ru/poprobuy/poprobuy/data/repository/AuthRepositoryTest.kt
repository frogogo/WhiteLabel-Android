package ru.poprobuy.poprobuy.data.repository

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationRequest
import ru.poprobuy.poprobuy.data.model.api.auth.ConfirmationCodeRequest
import ru.poprobuy.poprobuy.data.model.api.auth.TokenRefreshRequest
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.data.preferences.UserPreferences
import ru.poprobuy.poprobuy.util.Result

@ExperimentalCoroutinesApi
class AuthRepositoryTest {

  private lateinit var repository: AuthRepository

  private val api: PoprobuyApi = mockk(relaxed = true)
  private val userPreferences: UserPreferences = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    repository = AuthRepository(
      api = api,
      userPreferences = userPreferences
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun requestConfirmationCode() = runBlockingTest {
    val responseBody = DataFixtures.confirmationCodeRequestResponse
    coEvery { api.requestPasswordCode(any()) } returns Response.success(responseBody)

    val result = repository.requestConfirmationCode(DataFixtures.PHONE_NUMBER)

    result shouldBeEqualTo Result.Success(responseBody)
    coVerifySequence {
      api.requestPasswordCode(ConfirmationCodeRequest(DataFixtures.PHONE_NUMBER))
    }
    confirmVerified()
  }

  @Test
  fun authenticate() = runBlockingTest {
    val responseBody = DataFixtures.authenticationResponse
    coEvery { api.authenticate(any()) } returns Response.success(responseBody)

    val result = repository.authenticate(
      phoneNumber = DataFixtures.PHONE_NUMBER,
      password = DataFixtures.SMS_CODE
    )

    result shouldBeEqualTo Result.Success(responseBody)
    coVerifySequence {
      api.authenticate(
        AuthenticationRequest(
          phoneNumber = DataFixtures.PHONE_NUMBER,
          password = DataFixtures.SMS_CODE
        )
      )
    }
    confirmVerified()
  }

  @Test
  fun refreshToken() = runBlockingTest {
    val responseBody = DataFixtures.authenticationResponse
    coEvery { api.refreshToken(any()) } returns Response.success(responseBody)

    val result = repository.refreshToken(DataFixtures.REFRESH_TOKEN)

    result shouldBeEqualTo Result.Success(responseBody)
    coVerifySequence {
      api.refreshToken(TokenRefreshRequest(DataFixtures.REFRESH_TOKEN))
    }
    confirmVerified()
  }

  @Test
  fun `policy state is stored to shared preferences`() {
    repository.setPolicyAccepted()

    verify { userPreferences.policyAccepted = true }
    confirmVerified()
  }

  @Test
  fun `auth token is stored to shared preferences`() {
    repository.saveAuthTokens(DataFixtures.ACCESS_TOKEN, DataFixtures.REFRESH_TOKEN)

    verify {
      userPreferences.accessToken = DataFixtures.ACCESS_TOKEN
      userPreferences.refreshToken = DataFixtures.REFRESH_TOKEN
    }
    confirmVerified()
  }

  @Test
  fun `authorization state is stored to shared preferences`() {
    repository.setUserAuthorized()

    verify { userPreferences.isLoggedIn = true }
    confirmVerified()
  }

  @Test
  fun `data is cleared on logout`() {
    repository.logout()

    verify { userPreferences.clearData() }
    confirmVerified()
  }

  private fun confirmVerified() {
    confirmVerified(
      api,
      userPreferences
    )
  }

}
