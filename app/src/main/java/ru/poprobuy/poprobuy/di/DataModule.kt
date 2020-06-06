package ru.poprobuy.poprobuy.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.poprobuy.poprobuy.data.local.UserPreferences

val dataModule = module {
  // Preferences
  single { UserPreferences(androidContext()) }
}
