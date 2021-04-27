package ru.frogogo.whitelabel.util

import android.content.Context
import ru.frogogo.whitelabel.BuildConfig
import ru.frogogo.whitelabel.R

class ProfileUtils(private val context: Context) {

  /**
   * @return text with app version and build time
   */
  fun getAboutVersionText(): String {
    val versionName = BuildConfig.VERSION_NAME
    val versionCode = BuildConfig.VERSION_CODE
    val date = BuildConfig.BUILD_TIME.toSimpleDate()

    return context.getString(R.string.profile_app_version, versionName, versionCode, date)
  }
}
