package ru.poprobuy.poprobuy.data.preferences

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

  var onboardingCompleted: Boolean
    get() = sharedPrefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    set(value) = sharedPrefs.edit { putBoolean(KEY_ONBOARDING_COMPLETED, value) }

  var policyAccepted: Boolean
    get() = sharedPrefs.getBoolean(KEY_POLICY_ACCEPTED, false)
    set(value) = sharedPrefs.edit { putBoolean(KEY_POLICY_ACCEPTED, value) }

  companion object {
    private const val PREFERENCES_FILENAME = "user_preferences"

    // Keys
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    private const val KEY_POLICY_ACCEPTED = "policy_accepted"
  }

}
