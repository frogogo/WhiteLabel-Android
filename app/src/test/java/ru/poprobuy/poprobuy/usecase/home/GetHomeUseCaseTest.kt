package ru.poprobuy.poprobuy.usecase.home

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.repository.HomeRepository
import ru.poprobuy.poprobuy.failureNetworkCall
import ru.poprobuy.poprobuy.successNetworkCall
import ru.poprobuy.poprobuy.usecase.UseCaseResult

@ExperimentalCoroutinesApi
class GetHomeUseCaseTest {

  private lateinit var useCase: GetHomeUseCase
  private val homeRepository: HomeRepository = mockk(relaxed = true)

  @Before
  fun startUp() {
    clearAllMocks()
    useCase = GetHomeUseCase(homeRepository)
  }

  @Test
  fun `use case return home`() = runBlockingTest {
    val home = DataFixtures.home
    coEvery { homeRepository.getHome() } returns successNetworkCall(home)

    val result = useCase()

    result shouldBeInstanceOf UseCaseResult.Success::class
    (result as UseCaseResult.Success).data shouldBeEqualTo home
  }

  @Test
  fun `use case return failure`() = runBlockingTest {
    coEvery { homeRepository.getHome() } returns failureNetworkCall()

    val result = useCase()

    result shouldBeInstanceOf UseCaseResult.Failure::class
  }

}
