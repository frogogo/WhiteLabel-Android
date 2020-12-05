package ru.poprobuy.poprobuy.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.poprobuy.poprobuy.data.SessionStorage
import ru.poprobuy.poprobuy.data.preferences.UserPreferences
import ru.poprobuy.poprobuy.data.repository.*

val dataModule = module {
  // Preferences
  single { UserPreferences(androidContext()) }

  // Storage
  single { SessionStorage() }

  // Repository
  single { OnboardingRepository(get()) }
  single { AuthRepository(get(), get(), get()) }
  single { UserRepository(get(), get(), get()) }
  single { ReceiptsRepository(get(), get()) }
  single { HomeRepository(get(), get()) }
  single { VendingMachineRepository(get(), get()) }
}
