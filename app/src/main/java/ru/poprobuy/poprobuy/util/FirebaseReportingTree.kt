package ru.poprobuy.poprobuy.util

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class FirebaseReportingTree : Timber.Tree() {

  override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
    // Log timber message
    FirebaseCrashlytics.getInstance().log(message)

    if (priority != Log.VERBOSE && priority != Log.DEBUG) {
      // Log exception if it exists
      throwable?.let(FirebaseCrashlytics.getInstance()::recordException)
    }
  }

}
