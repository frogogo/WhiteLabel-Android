package ru.poprobuy.poprobuy.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.poprobuy.poprobuy.data.preferences.UserPreferences
import ru.poprobuy.poprobuy.data.repository.OnboardingRepository

val dataModule = module {
  // Preferences
  single { UserPreferences(androidContext()) }

  // Repository
  single { OnboardingRepository(get()) }
}
