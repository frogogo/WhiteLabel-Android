package ru.poprobuy.poprobuy.usecase.auth

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.NetworkResource

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
    coEvery {
      authRepository.requestConfirmationCode(any())
    } returns NetworkResource.Success(Response.success(Unit), Unit)

    val result = useCase("")

    result shouldBeEqualTo RequestConfirmationResult.Success
  }

  @Test
  fun `error returned if request failed`() = runBlockingTest {
    coEvery {
      authRepository.requestConfirmationCode(any())
    } returns NetworkResource.Error(null, NetworkError.Unknown())

    val result = useCase("")

    result shouldBeEqualTo RequestConfirmationResult.Error
  }

  @Test
  fun `too many requests returned if request failed with 429 status code`() = runBlockingTest {
    coEvery {
      authRepository.requestConfirmationCode(any())
    } returns NetworkResource.Error<Unit, Any>(null, NetworkError.HttpError(429, null))

    val result = useCase("")

    result shouldBeEqualTo RequestConfirmationResult.TooManyRequests
  }

}
