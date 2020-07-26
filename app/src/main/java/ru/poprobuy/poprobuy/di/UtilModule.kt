package ru.poprobuy.poprobuy.di

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.poprobuy.poprobuy.arch.navigation.NavigationRouter
import ru.poprobuy.poprobuy.util.OtpRequestDisabler
import ru.poprobuy.poprobuy.util.ProfileUtils

val utilModule = module {
  single { ProfileUtils(androidContext()) }
  factory { (activity: AppCompatActivity, navController: NavController) ->
    NavigationRouter(
      activity = activity,
      controller = navController
    )
  }
  factory { OtpRequestDisabler() }
}
