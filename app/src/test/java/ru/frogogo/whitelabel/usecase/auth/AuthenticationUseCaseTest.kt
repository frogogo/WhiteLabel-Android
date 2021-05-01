package ru.frogogo.whitelabel.usecase.auth

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.frogogo.test.DataFixtures
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.repository.AuthRepository
import ru.frogogo.whitelabel.data.repository.UserRepository
import ru.frogogo.test.testError
import ru.frogogo.whitelabel.util.network.NetworkError

@ExperimentalCoroutinesApi
class AuthenticationUseCaseTest {

  private lateinit var useCase: AuthenticationUseCase

  private val authRepository: AuthRepository = mockk(relaxed = true)
  private val userRepository: UserRepository = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    useCase = AuthenticationUseCase(
      authRepository = authRepository,
      userRepository = userRepository
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `user data is saved on success and new user is returned`() = runBlockingTest {
    val response = DataFixtures.authenticationResponse.copy(isNew = true, user = null)
    coEvery { authRepository.authenticate(any(), any()) } returns Result.Success(response)

    val result = useCase(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)

    coVerifySequence {
      authRepository.authenticate(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)
      authRepository.saveAuthTokens(response.accessToken, response.refreshToken)
    }
    confirmVerified()

    result shouldBeEqualTo AuthenticationResult.Success(true)
  }

  @Test
  fun `user data is saved on success and new user is not new returned`() = runBlockingTest {
    val response = DataFixtures.authenticationResponse.copy(isNew = false)
    coEvery { authRepository.authenticate(any(), any()) } returns Result.Success(response)

    val result = useCase(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)

    coVerifySequence {
      authRepository.authenticate(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)
      authRepository.saveAuthTokens(response.accessToken, response.refreshToken)
      authRepository.setUserAuthorized()
      userRepository.saveUser(response.user!!)
    }
    confirmVerified()

    result shouldBeEqualTo AuthenticationResult.Success(false)
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

  private fun confirmVerified() {
    confirmVerified(authRepository, userRepository)
  }
}
