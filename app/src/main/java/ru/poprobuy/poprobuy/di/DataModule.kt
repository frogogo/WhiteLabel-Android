@file:Suppress("USELESS_CAST")

package ru.poprobuy.poprobuy.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.poprobuy.poprobuy.data.SessionStorage
import ru.poprobuy.poprobuy.data.preferences.UserPreferences
import ru.poprobuy.poprobuy.data.preferences.UserPreferencesImpl
import ru.poprobuy.poprobuy.data.repository.*

val dataModule = module {
  // Preferences
  single { UserPreferencesImpl(androidContext()) as UserPreferences }

  // Storage
  single { SessionStorage() }

  // Repository
  single { OnboardingRepositoryImpl(get(), get()) as OnboardingRepository }
  single { AuthRepositoryImpl(get(), get(), get()) as AuthRepository }
  single { UserRepositoryImpl(get(), get(), get()) as UserRepository }
  single { ReceiptsRepositoryImpl(get(), get()) as ReceiptsRepository }
  single { HomeRepositoryImpl(get(), get()) as HomeRepository }
  single { VendingMachineRepositoryImpl(get(), get()) as VendingMachineRepository }
}
