package ru.poprobuy.poprobuy.work

import android.content.Context
import androidx.work.*
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.poprobuy.poprobuy.usecase.auth.RefreshTokenWorkerUseCase
import java.util.concurrent.TimeUnit

@OptIn(KoinApiExtension::class)
class TokenRefreshWorker(
  appContext: Context,
  workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams), KoinComponent {

  private val refreshTokenWorkerUseCase: RefreshTokenWorkerUseCase by inject()

  override suspend fun doWork(): Result {
    val result = refreshTokenWorkerUseCase()

    if (result is Result.Retry && runAttemptCount > MAX_RUN_ATTEMPTS_BEFORE_SUCCESS) {
      return Result.success()
    }
    return result
  }

  companion object {
    private const val NAME = "TokenRefreshWorker"
    private const val INTERVAL_DAYS = 2L
    private const val FLEX_INTERVAL_DAYS = 1L
    private const val MAX_RUN_ATTEMPTS_BEFORE_SUCCESS = 3

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
