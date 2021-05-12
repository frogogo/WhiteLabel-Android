package ru.frogogo.whitelabel.usecase.auth

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.frogogo.test.DataFixtures
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.repository.AuthRepository
import ru.frogogo.whitelabel.data.repository.UserRepository
import ru.frogogo.test.testError
import ru.frogogo.whitelabel.util.network.NetworkError

@ExperimentalCoroutinesApi
internal class RefreshTokenUseCaseTest {

  private lateinit var useCase: RefreshTokenUseCase

  private val authRepository: AuthRepository = mockk(relaxed = true)
  private val userRepository: UserRepository = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    useCase = RefreshTokenUseCase(
      authRepository = authRepository,
      userRepository = userRepository
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `useCase should refresh token`() = runBlockingTest {
    val response = DataFixtures.authenticationResponse
    every { authRepository.getRefreshToken() } returns DataFixtures.REFRESH_TOKEN
    coEvery { authRepository.refreshToken(any()) } returns Result.Success(response)

    val result = useCase()

    result shouldBeEqualTo response.accessToken
    coVerifySequence {
      authRepository.getRefreshToken()
      authRepository.refreshToken(DataFixtures.REFRESH_TOKEN)
      authRepository.saveAuthTokens(accessToken = response.accessToken, refreshToken = response.refreshToken)
      userRepository.saveUser(response.user!!)
    }
    confirmVerified()
  }

  @Test
  fun `useCase should return null when refreshToken does not exist`() = runBlockingTest {
    every { authRepository.getRefreshToken() } returns null

    val result = useCase()

    result.shouldBeNull()
    coVerifySequence {
      authRepository.getRefreshToken()
    }
    confirmVerified()
  }

  @Test
  fun `useCase should return null token on failure`() = runBlockingTest {
    every { authRepository.getRefreshToken() } returns DataFixtures.REFRESH_TOKEN
    coEvery { authRepository.refreshToken(any()) } returns Result.Failure(NetworkError.testError())

    val result = useCase()

    result.shouldBeNull()
    coVerifySequence {
      authRepository.getRefreshToken()
      authRepository.refreshToken(DataFixtures.REFRESH_TOKEN)
    }
    confirmVerified()
  }

  private fun confirmVerified() {
    confirmVerified(authRepository, userRepository)
  }
}
