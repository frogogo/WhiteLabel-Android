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
  single { AuthRepository(get(), get()) }
  single { UserRepository(get(), get()) }
  single { ReceiptsRepository(get()) }
  single { HomeRepository(get()) }
  single { VendingMachineRepository(get()) }
}
