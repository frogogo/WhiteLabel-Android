package ru.poprobuy.poprobuy.usecase.user

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.usecase.UseCaseResult
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.NetworkResource

@ExperimentalCoroutinesApi
class UpdateUserDetailsUseCaseTest {

  private lateinit var useCase: UpdateUserDetailsUseCase
  private val userRepository: UserRepository = mockk(relaxed = true)

  @Before
  fun startUp() {
    clearAllMocks()
    useCase = UpdateUserDetailsUseCase(userRepository)
  }

  @Test
  fun `success result returned if request executed successfully`() = runBlockingTest {
    coEvery {
      userRepository.updateUser(any(), any())
    } returns NetworkResource.Success(Response.success(Unit), Unit)

    val result = useCase("", "")

    result shouldBeInstanceOf UseCaseResult.Success::class
    coVerifySequence {
      userRepository.updateUser(any(), any())
    }
  }

  @Test
  fun `error returned if request failed`() = runBlockingTest {
    coEvery {
      userRepository.updateUser(any(), any())
    } returns NetworkResource.Error(null, NetworkError.Unknown())

    val result = useCase("", "")

    result shouldBeInstanceOf UseCaseResult.Failure::class
    coVerifySequence {
      userRepository.updateUser(any(), any())
    }
  }

}
