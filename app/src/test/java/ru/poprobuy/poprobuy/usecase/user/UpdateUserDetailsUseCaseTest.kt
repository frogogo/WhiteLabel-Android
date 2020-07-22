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
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.failureNetworkCall
import ru.poprobuy.poprobuy.successNetworkCall
import ru.poprobuy.poprobuy.usecase.UseCaseResult

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
    coEvery { userRepository.updateUser(any(), any()) } returns successNetworkCall(Unit)

    val result = useCase("", "")

    result shouldBeInstanceOf UseCaseResult.Success::class
    coVerifySequence {
      userRepository.updateUser(any(), any())
    }
  }

  @Test
  fun `error returned if request failed`() = runBlockingTest {
    coEvery { userRepository.updateUser(any(), any()) } returns failureNetworkCall()

    val result = useCase("", "")

    result shouldBeInstanceOf UseCaseResult.Failure::class
    coVerifySequence {
      userRepository.updateUser(any(), any())
    }
  }

}
