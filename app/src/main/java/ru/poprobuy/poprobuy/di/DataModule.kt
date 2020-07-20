package ru.poprobuy.poprobuy.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.poprobuy.poprobuy.data.preferences.UserPreferences
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.data.repository.OnboardingRepository
import ru.poprobuy.poprobuy.data.repository.UserRepository

val dataModule = module {
  // Preferences
  single { UserPreferences(androidContext()) }

  // Repository
  single { OnboardingRepository(get()) }
  single { AuthRepository(get(), get()) }
  single { UserRepository(get()) }
}
