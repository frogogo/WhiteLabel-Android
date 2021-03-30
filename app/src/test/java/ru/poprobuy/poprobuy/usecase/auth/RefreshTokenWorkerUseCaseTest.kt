package ru.poprobuy.poprobuy.usecase.auth

import androidx.work.ListenableWorker
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.poprobuy.poprobuy.analytics.SystemEvents
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.util.analytics.AnalyticsManager

@ExperimentalCoroutinesApi
internal class RefreshTokenWorkerUseCaseTest {

  private lateinit var useCase: RefreshTokenWorkerUseCase

  private val authRepository: AuthRepository = mockk(relaxed = true)
  private val refreshTokenUseCase: RefreshTokenUseCase = mockk(relaxed = true)
  private val analyticsManager: AnalyticsManager = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    useCase = RefreshTokenWorkerUseCase(
      authRepository = authRepository,
      refreshTokenUseCase = refreshTokenUseCase,
      analyticsManager = analyticsManager
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `useCase should return success result when refresh token not exist`() = runBlockingTest {
    every { authRepository.getRefreshToken() } returns null

    val result = useCase()

    result shouldBeEqualTo ListenableWorker.Result.success()
    coVerifySequence {
      authRepository.getRefreshToken()
      analyticsManager.logEvent(SystemEvents.TokenRefreshWork(SystemEvents.TokenRefreshWork.Result.SKIPPED))
    }
    confirmVerified()
  }

  @Test
  fun `useCase should return success result when token refreshed`() = runBlockingTest {
    every { authRepository.getRefreshToken() } returns "token"
    coEvery { refreshTokenUseCase() } returns "token"

    val result = useCase()

    result shouldBeEqualTo ListenableWorker.Result.success()
    coVerifySequence {
      authRepository.getRefreshToken()
      refreshTokenUseCase()
      analyticsManager.logEvent(SystemEvents.TokenRefreshWork(SystemEvents.TokenRefreshWork.Result.REFRESHED))
    }
    confirmVerified()
  }

  @Test
  fun `useCase should return return result when token refresh failed`() = runBlockingTest {
    every { authRepository.getRefreshToken() } returns "token"
    coEvery { refreshTokenUseCase() } returns null

    val result = useCase()

    result shouldBeEqualTo ListenableWorker.Result.retry()
    coVerifySequence {
      authRepository.getRefreshToken()
      refreshTokenUseCase()
      analyticsManager.logEvent(SystemEvents.TokenRefreshWork(SystemEvents.TokenRefreshWork.Result.FAILED))
    }
    confirmVerified()
  }

  private fun confirmVerified() {
    confirmVerified(
      authRepository,
      refreshTokenUseCase,
      analyticsManager
    )
  }
}
