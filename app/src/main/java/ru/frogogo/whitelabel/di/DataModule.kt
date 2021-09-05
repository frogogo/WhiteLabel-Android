@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.frogogo.whitelabel.data.preferences.UserPreferences
import ru.frogogo.whitelabel.data.preferences.UserPreferencesImpl
import ru.frogogo.whitelabel.data.repository.AuthRepository
import ru.frogogo.whitelabel.data.repository.AuthRepositoryImpl
import ru.frogogo.whitelabel.data.repository.HomeRepository
import ru.frogogo.whitelabel.data.repository.HomeRepositoryImpl
import ru.frogogo.whitelabel.data.repository.ItemsRepository
import ru.frogogo.whitelabel.data.repository.ItemsRepositoryImpl
import ru.frogogo.whitelabel.data.repository.OnboardingRepository
import ru.frogogo.whitelabel.data.repository.OnboardingRepositoryImpl
import ru.frogogo.whitelabel.data.repository.ReceiptsRepository
import ru.frogogo.whitelabel.data.repository.ReceiptsRepositoryImpl
import ru.frogogo.whitelabel.data.repository.UserRepository
import ru.frogogo.whitelabel.data.repository.UserRepositoryImpl

val dataModule = module {
  // Preferences
  single { UserPreferencesImpl(androidContext()) as UserPreferences }

  // Repository
  single { OnboardingRepositoryImpl(get(), get()) as OnboardingRepository }
  single { AuthRepositoryImpl(get(), get(), get()) as AuthRepository }
  single { UserRepositoryImpl(get(), get(), get()) as UserRepository }
  single { ReceiptsRepositoryImpl(get(), get()) as ReceiptsRepository }
  single { HomeRepositoryImpl(get(), get()) as HomeRepository }
  single { ItemsRepositoryImpl(get(), get()) as ItemsRepository }
}
