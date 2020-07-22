package ru.poprobuy.poprobuy.data.preferences

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import com.skydoves.whatif.whatIfNotNull
import com.squareup.moshi.Moshi
import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.data.model.api.user.UserJsonAdapter
import ru.poprobuy.poprobuy.extension.fromJsonOrNull
import ru.poprobuy.poprobuy.util.Constants

class UserPreferences(context: Context) {

  private val moshi = Moshi.Builder().build()
  private val sharedPrefs = EncryptedSharedPreferences.create(
    PREFERENCES_FILENAME, Constants.KEY_ALIAS, context,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
  )

  var accessToken: String?
    get() = sharedPrefs.getString(KEY_ACCESS_TOKEN, null)
    set(value) = sharedPrefs.edit { putString(KEY_ACCESS_TOKEN, value) }

  var isLoggedIn: Boolean
    get() = sharedPrefs.getBoolean(KEY_IS_LOGGED_IN, false)
    set(value) = sharedPrefs.edit { putBoolean(KEY_IS_LOGGED_IN, value) }

  var onboardingCompleted: Boolean
    get() = sharedPrefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    set(value) = sharedPrefs.edit { putBoolean(KEY_ONBOARDING_COMPLETED, value) }

  var policyAccepted: Boolean
    get() = sharedPrefs.getBoolean(KEY_POLICY_ACCEPTED, false)
    set(value) = sharedPrefs.edit { putBoolean(KEY_POLICY_ACCEPTED, value) }

  var user: User?
    get() {
      val json = sharedPrefs.getString(KEY_USER, null)
      return UserJsonAdapter(moshi).fromJsonOrNull(json)
    }
    set(value) = value.whatIfNotNull(
      whatIf = {
        val json = UserJsonAdapter(moshi).toJson(value)
        sharedPrefs.edit { putString(KEY_USER, json) }
      }, whatIfNot = {
        sharedPrefs.edit { putString(KEY_USER, null) }
      }
    )

  fun clearData() {
    sharedPrefs.edit { clear() }
  }

  companion object {
    private const val PREFERENCES_FILENAME = "user_preferences"

    // Keys
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    private const val KEY_POLICY_ACCEPTED = "policy_accepted"
    private const val KEY_USER = "user"
  }

}
