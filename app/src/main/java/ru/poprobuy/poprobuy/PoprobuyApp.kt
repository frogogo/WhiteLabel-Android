package ru.poprobuy.poprobuy

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.DebugLogger
import com.github.ajalt.timberkt.Timber
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.poprobuy.poprobuy.di.appComponent
import ru.poprobuy.poprobuy.extension.getActivityManager
import ru.poprobuy.poprobuy.extension.getWorkManager
import ru.poprobuy.poprobuy.util.FirebaseReportingTree
import ru.poprobuy.poprobuy.work.TokenRefreshWorker

@Suppress("unused")
class PoprobuyApp : Application(), ImageLoaderFactory {

  override fun onCreate() {
    super.onCreate()
    initLogger()
    initKoin()
    scheduleWorks()
  }

  override fun newImageLoader(): ImageLoader {
    return ImageLoader.Builder(applicationContext)
      .crossfade(true) // Show a short crossfade when loading images from network or disk.
      .allowRgb565(getActivityManager().isLowRamDevice)
      .apply {
        // Enable logging to the standard Android log if this is a debug build.
        if (BuildConfig.DEBUG) {
          logger(DebugLogger())
        }
      }
      .build()
  }

  private fun initLogger() {
    Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else FirebaseReportingTree())
  }

  private fun initKoin() {
    startKoin {
      androidContext(applicationContext)
      modules(appComponent)
    }
  }

  private fun scheduleWorks() {
    TokenRefreshWorker.enqueue(applicationContext.getWorkManager())
  }

}
