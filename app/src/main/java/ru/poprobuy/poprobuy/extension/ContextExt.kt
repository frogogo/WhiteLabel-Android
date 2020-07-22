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
import androidx.browser.customtabs.CustomTabsIntent
import com.github.ajalt.timberkt.e
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.util.CustomTabsPackageHelper

fun Context.showKeyboard(view: View) {
  getInputMethodManager().showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
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

/**
 * Opens url in custom tabs
 */
fun Context.openUrlInTabs(url: String, onFailed: () -> Unit = {}) {
  // Strictly require custom tabs-compatible browser
  val packageName = CustomTabsPackageHelper.getPackageNameToUse(this)
  if (packageName == null) {
    onFailed.invoke()
    return
  }

  val intent = CustomTabsIntent.Builder().apply {
    addDefaultShareMenuItem()
    setShowTitle(true)
    setToolbarColor(getColor(R.color.status_bar))
  }.build()

  // Set package name to use
  intent.intent.setPackage(packageName)

  try {
    intent.launchUrl(this, Uri.parse(url))
  } catch (ex: Exception) {
    e(ex) { "No activity was found to handle this intent" }
    onFailed()
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
