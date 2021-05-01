package ru.frogogo.whitelabel.usecase.auth

import androidx.work.ListenableWorker
import ru.frogogo.whitelabel.analytics.SystemEvents.TokenRefreshWork
import ru.frogogo.whitelabel.data.repository.AuthRepository
import ru.frogogo.whitelabel.util.analytics.AnalyticsManager

class RefreshTokenWorkerUseCase(
  private val authRepository: AuthRepository,
  private val refreshTokenUseCase: RefreshTokenUseCase,
  private val analyticsManager: AnalyticsManager,
) {

  suspend operator fun invoke(): ListenableWorker.Result {
    if (authRepository.getRefreshToken().isNullOrBlank()) {
      logResult(TokenRefreshWork.Result.SKIPPED)
      return ListenableWorker.Result.success()
    }

    return when (refreshTokenUseCase()) {
      null -> {
        logResult(TokenRefreshWork.Result.FAILED)
        ListenableWorker.Result.retry()
      }
      else -> {
        logResult(TokenRefreshWork.Result.REFRESHED)
        ListenableWorker.Result.success()
      }
    }
  }

  private fun logResult(result: TokenRefreshWork.Result) {
    analyticsManager.logEvent(TokenRefreshWork(result))
  }
}
