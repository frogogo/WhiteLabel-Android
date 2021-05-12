package ru.frogogo.whitelabel.usecase.auth

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.api.auth.ConfirmationCodeRequestResponse
import ru.frogogo.whitelabel.data.repository.AuthRepository
import ru.frogogo.whitelabel.util.network.HttpErrorReason.ERROR_REASON_PASSWORD_REFRESH_RATE_LIMIT
import ru.frogogo.whitelabel.util.network.NetworkError
import ru.frogogo.test.test422Error
import ru.frogogo.test.testError

@ExperimentalCoroutinesApi
class RequestConfirmationCodeUseCaseTest {

  private lateinit var useCase: RequestConfirmationCodeUseCase
  private val authRepository: AuthRepository = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    useCase = RequestConfirmationCodeUseCase(authRepository)
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `success result returned if request executed successfully`() = runBlockingTest {
    val response = ConfirmationCodeRequestResponse(60)
    coEvery { authRepository.requestConfirmationCode(any()) } returns Result.Success(response)

    val result = useCase("")

    result shouldBeEqualTo RequestConfirmationResult.Success(60)
  }

  @Test
  fun `error returned if request failed`() = runBlockingTest {
    coEvery { authRepository.requestConfirmationCode(any()) } returns Result.Failure(NetworkError.testError())

    val result = useCase("")

    result shouldBeEqualTo RequestConfirmationResult.Error
  }

  @Test
  fun `too many requests error should be returned`() = runBlockingTest {
    coEvery { authRepository.requestConfirmationCode(any()) }
      .returns(Result.Failure(NetworkError.test422Error(ERROR_REASON_PASSWORD_REFRESH_RATE_LIMIT)))

    val result = useCase("")

    result shouldBeEqualTo RequestConfirmationResult.TooManyRequests
  }
}
