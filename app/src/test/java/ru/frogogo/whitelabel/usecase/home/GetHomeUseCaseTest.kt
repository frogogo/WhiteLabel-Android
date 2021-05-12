package ru.frogogo.whitelabel.usecase.home

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
import ru.frogogo.test.DataFixtures
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.mapper.toDomain
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.data.repository.HomeRepository
import ru.frogogo.whitelabel.util.network.NetworkError

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
