package ru.frogogo.whitelabel.usecase.user

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.repository.UserRepository
import ru.frogogo.test.testError
import ru.frogogo.whitelabel.util.network.NetworkError

@ExperimentalCoroutinesApi
class UpdateUserDetailsUseCaseTest {

  private lateinit var useCase: UpdateUserDetailsUseCase
  private val userRepository: UserRepository = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    useCase = UpdateUserDetailsUseCase(userRepository)
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `success result returned if request executed successfully`() = runBlockingTest {
    val response = Result.Success<Unit, NetworkError<ErrorResponse>>(Unit)
    coEvery { userRepository.updateUser(any(), any()) } returns response

    val result = useCase("", "")

    result shouldBeEqualTo response
    coVerifySequence {
      userRepository.updateUser(any(), any())
    }
  }

  @Test
  fun `error returned if request failed`() = runBlockingTest {
    val response = Result.Failure<Unit, NetworkError<ErrorResponse>>(NetworkError.testError())
    coEvery { userRepository.updateUser(any(), any()) } returns response

    val result = useCase("", "")

    result shouldBeEqualTo response
    coVerifySequence {
      userRepository.updateUser(any(), any())
    }
  }
}
