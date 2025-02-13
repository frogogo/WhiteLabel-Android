package ru.frogogo.whitelabel.usecase.user

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.frogogo.test.DataFixtures
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.api.user.User
import ru.frogogo.whitelabel.data.repository.UserRepository
import ru.frogogo.test.testError
import ru.frogogo.whitelabel.util.network.NetworkError

@ExperimentalCoroutinesApi
class GetUserInfoUseCaseTest {

  private lateinit var useCase: GetUserInfoUseCase
  private val userRepository: UserRepository = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    useCase = GetUserInfoUseCase(userRepository)
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `use case return user and saves it to repository`() = runBlockingTest {
    val user = DataFixtures.user
    val response = Result.Success<User, NetworkError<ErrorResponse>>(user)
    coEvery { userRepository.fetchUser() } returns response

    val result = useCase()

    result shouldBeEqualTo response
    coVerifySequence {
      userRepository.fetchUser()
      userRepository.saveUser(DataFixtures.user)
    }
  }

  @Test
  fun `use case return failure and doesn't touch repository`() = runBlockingTest {
    val response = Result.Failure<User, NetworkError<ErrorResponse>>(NetworkError.testError())
    coEvery { userRepository.fetchUser() } returns response

    val result = useCase()

    result shouldBeEqualTo response
    coVerifySequence {
      userRepository.fetchUser()
    }
    verify(exactly = 0) {
      userRepository.saveUser(any())
    }
  }
}
