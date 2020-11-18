package ru.poprobuy.poprobuy.usecase.auth

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.testError
import ru.poprobuy.poprobuy.util.Result
import ru.poprobuy.poprobuy.util.network.NetworkError

@ExperimentalCoroutinesApi
class AuthenticationUseCaseTest {

  private lateinit var useCase: AuthenticationUseCase

  private val authRepository: AuthRepository = mockk(relaxed = true)
  private val userRepository: UserRepository = mockk(relaxed = true)

  @Before
  fun startUp() {
    clearAllMocks()
    useCase = AuthenticationUseCase(
      authRepository = authRepository,
      userRepository = userRepository
    )
  }

  @Test
  fun `user data is saved on success and new user is returned`() = runBlockingTest {
    testSuccess(true)
  }

  @Test
  fun `user data is saved on success and new user is not new returned`() = runBlockingTest {
    testSuccess(false)
  }

  @Test
  fun `user was not found returned on 404 error`() = runBlockingTest {
    coEvery { authRepository.authenticate(any(), any()) } returns Result.Failure(NetworkError.testError(404))

    val result = useCase(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)

    coVerifySequence {
      authRepository.authenticate(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)
    }
    confirmVerified()

    result shouldBeEqualTo AuthenticationResult.NotFound
  }

  @Test
  fun `error returned on failure execution`() = runBlockingTest {
    coEvery { authRepository.authenticate(any(), any()) } returns Result.Failure(NetworkError.testError())

    val result = useCase(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)

    coVerifySequence {
      authRepository.authenticate(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)
    }
    confirmVerified()

    result shouldBeEqualTo AuthenticationResult.Error
  }

  private fun testSuccess(isNew: Boolean) = runBlocking {
    val response = DataFixtures.authenticationResponse.copy(isNew = isNew)
    coEvery { authRepository.authenticate(any(), any()) } returns Result.Success(response)

    val result = useCase(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)

    coVerifySequence {
      authRepository.authenticate(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)
      authRepository.saveAuthTokens(response.accessToken, response.refreshToken)
      authRepository.setUserAuthorized()
      userRepository.saveUser(response.user)
    }
    confirmVerified()

    result shouldBeEqualTo AuthenticationResult.Success(isNew)
  }

  private fun confirmVerified() {
    confirmVerified(authRepository, userRepository)
  }

}
