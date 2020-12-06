package ru.poprobuy.poprobuy.data.preferences

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import com.squareup.moshi.Moshi
import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.util.Constants
import ru.poprobuy.poprobuy.util.boolean
import ru.poprobuy.poprobuy.util.json
import ru.poprobuy.poprobuy.util.stringNullable

class UserPreferencesImpl(context: Context) : UserPreferences {

  private val moshi = Moshi.Builder().build()
  private val preferences = EncryptedSharedPreferences.create(
    PREFERENCES_FILENAME, Constants.KEY_ALIAS, context,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
  )

  // Auth
  override var accessToken: String? by preferences.stringNullable(KEY_ACCESS_TOKEN)
  override var refreshToken: String? by preferences.stringNullable(KEY_REFRESH_TOKEN)
  override var isLoggedIn: Boolean by preferences.boolean(KEY_IS_LOGGED_IN)

  // States
  override var onboardingCompleted: Boolean by preferences.boolean(KEY_ONBOARDING_COMPLETED)
  override var policyAccepted: Boolean by preferences.boolean(KEY_POLICY_ACCEPTED)

  // Data
  override var user: User? by preferences.json(moshi, KEY_USER, User::class)

  override fun clearData() {
    preferences.edit { clear() }
  }

  override fun clearUserData() {
    accessToken = null
    refreshToken = null
    isLoggedIn = false
    user = null
  }

  companion object {
    private const val PREFERENCES_FILENAME = "user_preferences"

    // Keys
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_REFRESH_TOKEN = "refresh_token"

    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    private const val KEY_POLICY_ACCEPTED = "policy_accepted"
    private const val KEY_USER = "user"
  }

}
