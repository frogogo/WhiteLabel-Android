package ru.frogogo.whitelabel.data.repository

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import ru.frogogo.test.DataFixtures
import ru.frogogo.test.base.RepositoryTest
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.mapper.toDomain
import ru.frogogo.whitelabel.data.network.FrogogoApi

@ExperimentalCoroutinesApi
internal class HomeRepositoryImplTest : RepositoryTest() {

  private lateinit var repository: HomeRepository

  private val api: FrogogoApi = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    repository = HomeRepositoryImpl(
      dispatcher = coroutineTestExtension.testDispatcherProvider,
      api = api
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun getHome() = runBlockingTest {
    val home = DataFixtures.home
    coEvery { api.getHome() } returns Response.success(home)

    val result = repository.getHome()

    result shouldBeEqualTo Result.Success(home.toDomain())
    coVerifySequence {
      api.getHome()
    }
    confirmVerified()
  }

  private fun confirmVerified() {
    confirmVerified(api)
  }
}
