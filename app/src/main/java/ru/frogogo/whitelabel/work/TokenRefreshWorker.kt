package ru.frogogo.whitelabel.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import ru.frogogo.whitelabel.usecase.auth.RefreshTokenWorkerUseCase
import java.util.concurrent.TimeUnit

class TokenRefreshWorker(
  appContext: Context,
  workerParams: WorkerParameters,
  private val refreshTokenWorkerUseCase: RefreshTokenWorkerUseCase,
) : CoroutineWorker(appContext, workerParams), KoinComponent {

  override suspend fun doWork(): Result {
    val result = refreshTokenWorkerUseCase()

    if (result is Result.Retry && runAttemptCount > MAX_RUN_ATTEMPTS) {
      return Result.success()
    }
    return result
  }

  companion object {
    private const val NAME = "TokenRefreshWorker"
    private const val INTERVAL_DAYS = 2L
    private const val FLEX_INTERVAL_DAYS = 1L
    private const val MAX_RUN_ATTEMPTS = 3

    fun enqueue(manager: WorkManager) {
      val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresCharging(true)
        .build()

      val work = PeriodicWorkRequestBuilder<TokenRefreshWorker>(
        repeatInterval = INTERVAL_DAYS,
        repeatIntervalTimeUnit = TimeUnit.DAYS,
        flexTimeInterval = FLEX_INTERVAL_DAYS,
        flexTimeIntervalUnit = TimeUnit.DAYS,
      ).setConstraints(constraints)
        .build()

      manager.enqueueUniquePeriodicWork(NAME, ExistingPeriodicWorkPolicy.KEEP, work)
    }
  }
}
