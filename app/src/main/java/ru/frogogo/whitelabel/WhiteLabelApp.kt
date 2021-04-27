package ru.frogogo.whitelabel

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.github.ajalt.timberkt.Timber
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.frogogo.whitelabel.di.appComponent
import ru.frogogo.whitelabel.extension.getWorkManager
import ru.frogogo.whitelabel.util.CoilImageLoaderFactory
import ru.frogogo.whitelabel.util.FirebaseReportingTree
import ru.frogogo.whitelabel.work.TokenRefreshWorker

@Suppress("unused")
class WhiteLabelApp : Application(), ImageLoaderFactory {

  override fun onCreate() {
    super.onCreate()
    initLogger()
    initKoin()
    scheduleWorks()
  }

  override fun newImageLoader(): ImageLoader =
    CoilImageLoaderFactory.create(applicationContext)

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
