package ru.poprobuy.poprobuy.usecase.user

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.failureNetworkCall
import ru.poprobuy.poprobuy.successNetworkCall
import ru.poprobuy.poprobuy.usecase.UseCaseResult

@ExperimentalCoroutinesApi
class GetUserInfoUseCaseTest {

  private lateinit var useCase: GetUserInfoUseCase
  private val userRepository: UserRepository = mockk(relaxed = true)

  @Before
  fun startUp() {
    clearAllMocks()
    useCase = GetUserInfoUseCase(userRepository)
  }

  @Test
  fun `use case return user and saves it to repository`() = runBlockingTest {
    val user = DataFixtures.user
    coEvery { userRepository.fetchUser() } returns successNetworkCall(user)

    val result = useCase()

    result shouldBeInstanceOf UseCaseResult.Success::class
    (result as UseCaseResult.Success).data shouldBeEqualTo user
    coVerifySequence {
      userRepository.fetchUser()
      userRepository.saveUser(DataFixtures.user)
    }
  }

  @Test
  fun `use case return failure and doesn't touch repository`() = runBlockingTest {
    coEvery { userRepository.fetchUser() } returns failureNetworkCall()

    val result = useCase()

    result shouldBeInstanceOf UseCaseResult.Failure::class
    coVerifySequence {
      userRepository.fetchUser()
    }
    verify(exactly = 0) {
      userRepository.saveUser(any())
    }
  }

}
