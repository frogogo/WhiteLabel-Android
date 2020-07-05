package ru.poprobuy.poprobuy.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.poprobuy.poprobuy.util.ProfileUtils

val utilModule = module {
  single { ProfileUtils(androidContext()) }
}
