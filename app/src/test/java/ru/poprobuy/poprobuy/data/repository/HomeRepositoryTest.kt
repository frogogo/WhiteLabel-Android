package ru.poprobuy.poprobuy.data.repository

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.mapper.toDomain
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.util.Result

@ExperimentalCoroutinesApi
internal class HomeRepositoryTest {

  private lateinit var repository: HomeRepository

  private val api: PoprobuyApi = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    repository = HomeRepository(
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
