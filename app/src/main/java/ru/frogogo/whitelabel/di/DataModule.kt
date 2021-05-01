@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.frogogo.whitelabel.data.preferences.UserPreferences
import ru.frogogo.whitelabel.data.preferences.UserPreferencesImpl
import ru.frogogo.whitelabel.data.repository.*

val dataModule = module {
  // Preferences
  single { UserPreferencesImpl(androidContext()) as UserPreferences }

  // Repository
  single { OnboardingRepositoryImpl(get(), get()) as OnboardingRepository }
  single { AuthRepositoryImpl(get(), get(), get()) as AuthRepository }
  single { UserRepositoryImpl(get(), get(), get()) as UserRepository }
  single { ReceiptsRepositoryImpl(get(), get()) as ReceiptsRepository }
  single { HomeRepositoryImpl(get(), get()) as HomeRepository }
}
