package ru.frogogo.whitelabel.di

import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module
import ru.frogogo.whitelabel.work.TokenRefreshWorker

val workerModule = module {
  worker { TokenRefreshWorker(get(), get(), get()) }
}
