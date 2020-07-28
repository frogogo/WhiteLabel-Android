package ru.poprobuy.poprobuy.usecase.auth

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.data.model.api.auth.ConfirmationCodeRequestResponse
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.failureHttpNetworkCall
import ru.poprobuy.poprobuy.failureNetworkCall
import ru.poprobuy.poprobuy.successNetworkCall

@ExperimentalCoroutinesApi
class RequestConfirmationCodeUseCaseTest {

  private lateinit var useCase: RequestConfirmationCodeUseCase
  private val authRepository: AuthRepository = mockk(relaxed = true)

  @Before
  fun startUp() {
    clearAllMocks()
    useCase = RequestConfirmationCodeUseCase(authRepository)
  }

  @Test
  fun `success result returned if request executed successfully`() = runBlockingTest {
    val response = ConfirmationCodeRequestResponse(60)
    coEvery { authRepository.requestConfirmationCode(any()) } returns successNetworkCall(response)

    val result = useCase("")

    result shouldBeEqualTo RequestConfirmationResult.Success(60)
  }

  @Test
  fun `error returned if request failed`() = runBlockingTest {
    coEvery { authRepository.requestConfirmationCode(any()) } returns failureNetworkCall()

    val result = useCase("")

    result shouldBeEqualTo RequestConfirmationResult.Error
  }

  @Test
  fun `too many requests returned if request failed with 429 status code`() = runBlockingTest {
    coEvery { authRepository.requestConfirmationCode(any()) } returns failureHttpNetworkCall(429)

    val result = useCase("")

    result shouldBeEqualTo RequestConfirmationResult.TooManyRequests
  }

}
