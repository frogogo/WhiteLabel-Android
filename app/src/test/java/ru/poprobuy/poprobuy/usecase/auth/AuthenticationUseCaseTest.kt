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
import ru.poprobuy.poprobuy.failureHttpNetworkCall
import ru.poprobuy.poprobuy.failureNetworkCall
import ru.poprobuy.poprobuy.successNetworkCall

@ExperimentalCoroutinesApi
class AuthenticationUseCaseTest {

  private lateinit var useCase: AuthenticationUseCase
  private val authRepository: AuthRepository = mockk(relaxed = true)

  @Before
  fun startUp() {
    clearAllMocks()
    useCase = AuthenticationUseCase(authRepository)
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
    coEvery { authRepository.authenticate(any(), any()) } returns failureHttpNetworkCall(404)

    val result = useCase(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)

    verify(exactly = 0) {
      authRepository.saveAuthToken(any())
      authRepository.setUserAuthorized()
    }
    coVerifySequence {
      authRepository.authenticate(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)
    }

    result shouldBeEqualTo AuthenticationResult.NotFound
  }

  @Test
  fun `error returned on failure execution`() = runBlockingTest {
    coEvery { authRepository.authenticate(any(), any()) } returns failureNetworkCall()

    val result = useCase(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)

    verify(exactly = 0) {
      authRepository.saveAuthToken(any())
      authRepository.setUserAuthorized()
    }
    coVerifySequence {
      authRepository.authenticate(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)
    }

    result shouldBeEqualTo AuthenticationResult.Error
  }

  private fun testSuccess(isNew: Boolean) = runBlocking {
    val response = DataFixtures.authenticationResponse.copy(isNew = isNew)
    coEvery { authRepository.authenticate(any(), any()) } returns successNetworkCall(response)

    val result = useCase(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)

    coVerifySequence {
      authRepository.authenticate(DataFixtures.PHONE_NUMBER, DataFixtures.SMS_CODE)
      authRepository.saveAuthToken(response.accessToken)
      authRepository.setUserAuthorized()
    }

    result shouldBeEqualTo AuthenticationResult.Success(isNew)
  }

}
