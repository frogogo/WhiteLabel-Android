package ru.poprobuy.poprobuy.usecase.home

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
import ru.poprobuy.test.DataFixtures
import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.data.mapper.toDomain
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.ui.home.HomeState
import ru.poprobuy.poprobuy.data.repository.HomeRepository
import ru.poprobuy.poprobuy.util.network.NetworkError

@ExperimentalCoroutinesApi
class GetHomeUseCaseTest {

  private lateinit var useCase: GetHomeUseCase
  private val homeRepository: HomeRepository = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    useCase = GetHomeUseCase(homeRepository)
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `useCase should return result`() = runBlockingTest {
    val response = Result.Success<HomeState, NetworkError<ErrorResponse>>(DataFixtures.home.toDomain())
    coEvery { homeRepository.getHome() } returns response

    val result = useCase()

    result shouldBeEqualTo response
    coVerifySequence {
      homeRepository.getHome()
    }
  }

}
