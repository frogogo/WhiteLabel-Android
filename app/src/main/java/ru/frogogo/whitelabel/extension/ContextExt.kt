package ru.frogogo.whitelabel.extension

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.work.WorkManager

fun Context.fetchDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

fun Context.showKeyboard(view: View) {
  getInputMethodManager().showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Shows screen of details about the application.
 */
fun Context.showAppDetailsSettings() {
  Intent(
    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
    Uri.fromParts("package", packageName, null),
  ).apply {
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
  }.also { intent ->
    startActivity(intent)
  }
}

fun Context.vibratePhone(duration: Long = 200) {
  val vibrator = getVibrator()
  if (!vibrator.hasVibrator()) return

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
  } else {
    @Suppress("DEPRECATION")
    vibrator.vibrate(duration)
  }
}

fun Context.getWorkManager(): WorkManager =
  WorkManager.getInstance(applicationContext)

// Services

fun Context.getInputMethodManager(): InputMethodManager = requireSystemService()

fun Context.getVibrator(): Vibrator = requireSystemService()

fun Context.getActivityManager(): ActivityManager = requireSystemService()

private inline fun <reified T : Any> Context.requireSystemService(): T {
  return checkNotNull(getSystemService()) { "System service of type ${T::class.java} was not found." }
}
