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
import ru.poprobuy.poprobuy.data.model.api.user.UserUpdateRequest
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.data.preferences.UserPreferences
import ru.poprobuy.poprobuy.util.Result

@ExperimentalCoroutinesApi
internal class UserRepositoryTest {

  private lateinit var userRepository: UserRepository

  private val api: PoprobuyApi = mockk(relaxed = true)
  private val userPreferences: UserPreferences = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    userRepository = UserRepository(
      api = api,
      userPreferences = userPreferences
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun updateUser() = runBlockingTest {
    coEvery { api.updateUser(any()) } returns Response.success(Unit)

    val result = userRepository.updateUser(
      email = DataFixtures.USER_EMAIL,
      name = DataFixtures.USER_NAME
    )

    result shouldBeEqualTo Result.Success(Unit)
    coVerifySequence {
      api.updateUser(UserUpdateRequest(DataFixtures.USER_EMAIL, DataFixtures.USER_NAME))
    }
    confirmVerified()
  }

  @Test
  fun fetchUser() = runBlockingTest {
    coEvery { api.getUser() } returns Response.success(DataFixtures.user)

    val result = userRepository.fetchUser()

    result shouldBeEqualTo Result.Success(DataFixtures.user)
    coVerifySequence {
      api.getUser()
    }
    confirmVerified()
  }

  @Test
  fun `getUser should return saved user`() {
    every { userPreferences.user } returns DataFixtures.user

    val user = userRepository.getUser()

    user shouldBeEqualTo DataFixtures.user
    coVerifySequence {
      userPreferences.user
    }
    confirmVerified()
  }

  @Test
  fun `saveUser should save user to preferences`() {
    userRepository.saveUser(DataFixtures.user)

    coVerifySequence {
      userPreferences.user = DataFixtures.user
    }
    confirmVerified()
  }

  private fun confirmVerified() {
    confirmVerified(
      api,
      userPreferences
    )
  }

}
