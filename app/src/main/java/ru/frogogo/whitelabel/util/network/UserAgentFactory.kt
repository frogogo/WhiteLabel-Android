package ru.frogogo.whitelabel.util.network

import android.os.Build
import ru.frogogo.whitelabel.BuildConfig

object UserAgentFactory {

  private const val PACKAGE_NAME = BuildConfig.APPLICATION_ID

  fun create(): String {
    val versionCode = BuildConfig.VERSION_CODE
    val versionName = BuildConfig.VERSION_NAME
    val androidVersion = Build.VERSION.RELEASE
    val retrofitVersion = BuildConfig.RETROFIT_VERSION

    return buildString {
      // App data
      append("Poprobuy/$versionName ")

      // App data 2
      append("($PACKAGE_NAME; build:$versionCode; Android $androidVersion) ")

      // Retrofit data
      append("Retrofit/$retrofitVersion")
    }
  }
}
