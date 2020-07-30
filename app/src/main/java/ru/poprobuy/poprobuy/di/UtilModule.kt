package ru.poprobuy.poprobuy.di

import androidx.navigation.NavController
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.poprobuy.poprobuy.arch.navigation.NavigationRouter
import ru.poprobuy.poprobuy.util.OtpRequestDisabler
import ru.poprobuy.poprobuy.util.ProfileUtils
import ru.poprobuy.poprobuy.util.ResourceProvider

val utilModule = module {
  single { ResourceProvider(androidContext()) }
  single { ProfileUtils(androidContext()) }
  factory { (navController: NavController) ->
    NavigationRouter(
      controller = navController
    )
  }
  factory { OtpRequestDisabler() }
}
