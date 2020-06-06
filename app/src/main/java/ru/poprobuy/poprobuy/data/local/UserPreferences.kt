package ru.poprobuy.poprobuy.data.local

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import ru.poprobuy.poprobuy.util.Constants

class UserPreferences(context: Context) {

  private val sharedPrefs = EncryptedSharedPreferences.create(
    PREFERENCES_FILENAME, Constants.KEY_ALIAS, context,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
  )

  var isLoggedIn: Boolean
    get() = sharedPrefs.getBoolean(KEY_IS_LOGGED_IN, false)
    set(value) = sharedPrefs.edit { putBoolean(KEY_IS_LOGGED_IN, value) }

  var onboardingViewed: Boolean
    get() = sharedPrefs.getBoolean(KEY_ONBOARDING_VIEWED, false)
    set(value) = sharedPrefs.edit { putBoolean(KEY_ONBOARDING_VIEWED, value) }

  companion object {
    private const val PREFERENCES_FILENAME = "user_preferences"

    // Keys
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_ONBOARDING_VIEWED = "onboarding_viewed"
  }

}
