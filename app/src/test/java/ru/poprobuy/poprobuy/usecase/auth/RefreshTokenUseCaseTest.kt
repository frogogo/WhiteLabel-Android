package ru.poprobuy.poprobuy.usecase.auth

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.testError
import ru.poprobuy.poprobuy.util.Result
import ru.poprobuy.poprobuy.util.network.NetworkError

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
      userRepository.saveUser(response.user)
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
