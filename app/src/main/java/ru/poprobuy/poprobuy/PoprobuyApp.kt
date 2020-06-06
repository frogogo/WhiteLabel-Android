package ru.poprobuy.poprobuy

import android.app.Application
import com.github.ajalt.timberkt.Timber
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.poprobuy.poprobuy.di.appComponent

@Suppress("unused")
class PoprobuyApp : Application() {

  override fun onCreate() {
    super.onCreate()
    initLogger()
    initKoin()
  }

  private fun initLogger() {
    Timber.plant(Timber.DebugTree())
  }

  private fun initKoin() {
    startKoin {
      androidLogger()
      androidContext(applicationContext)
      modules(appComponent)
    }
  }

}
