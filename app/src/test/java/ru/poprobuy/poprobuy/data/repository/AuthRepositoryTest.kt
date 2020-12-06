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
import ru.poprobuy.poprobuy.RepositoryTest
import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationRequest
import ru.poprobuy.poprobuy.data.model.api.auth.ConfirmationCodeRequest
import ru.poprobuy.poprobuy.data.model.api.auth.TokenRefreshRequest
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.data.preferences.UserPreferences

@ExperimentalCoroutinesApi
class AuthRepositoryTest : RepositoryTest() {

  private lateinit var repository: AuthRepository

  private val api: PoprobuyApi = mockk(relaxed = true)
  private val userPreferences: UserPreferences = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    repository = AuthRepository(
      dispatcher = coroutineTestExtension.testDispatcherProvider,
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
  fun `repository should return access token`() {
    every { userPreferences.accessToken } returns DataFixtures.ACCESS_TOKEN

    val token = repository.getAccessToken()

    token shouldBeEqualTo DataFixtures.ACCESS_TOKEN
    verifySequence {
      userPreferences.accessToken
    }
    confirmVerified()
  }

  @Test
  fun `repository should return refresh token`() {
    every { userPreferences.refreshToken } returns DataFixtures.REFRESH_TOKEN

    val token = repository.getRefreshToken()

    token shouldBeEqualTo DataFixtures.REFRESH_TOKEN
    verifySequence {
      userPreferences.refreshToken
    }
    confirmVerified()
  }

  @Test
  fun `repository should save policy accepted`() {
    repository.setPolicyAccepted()

    verify { userPreferences.policyAccepted = true }
    confirmVerified()
  }

  @Test
  fun `repository should save auth tokens`() {
    repository.saveAuthTokens(DataFixtures.ACCESS_TOKEN, DataFixtures.REFRESH_TOKEN)

    verify {
      userPreferences.accessToken = DataFixtures.ACCESS_TOKEN
      userPreferences.refreshToken = DataFixtures.REFRESH_TOKEN
    }
    confirmVerified()
  }

  @Test
  fun `repository should save auth state`() {
    repository.setUserAuthorized()

    verify { userPreferences.isLoggedIn = true }
    confirmVerified()
  }

  @Test
  fun `repository should clear data on logout`() {
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
