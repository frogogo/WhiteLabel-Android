package ru.poprobuy.poprobuy.extension

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DimenRes
import androidx.core.content.res.ResourcesCompat

fun Context.getFloat(@DimenRes id: Int): Float = ResourcesCompat.getFloat(resources, id)

fun Context.showKeyboard(view: View) {
  getInputMethodManager().showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.hideKeyboard() {
  getInputMethodManager().toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

/**
 * Shows screen of details about the application.
 */
fun Context.showAppDetailsSettings() {
  Intent(
    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
    Uri.fromParts("package", packageName, null)
  ).apply {
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
  }.also { intent ->
    startActivity(intent)
  }
}

fun Context.vibratePhone(duration: Long = 200) {
  val vibrator = getVibrator()
  if (!vibrator.hasVibrator()) return

  if (Build.VERSION.SDK_INT >= 26) {
    vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
  } else {
    @Suppress("DEPRECATION")
    vibrator.vibrate(duration)
  }
}

// Services
fun Context.getInputMethodManager() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun Context.getVibrator() = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
