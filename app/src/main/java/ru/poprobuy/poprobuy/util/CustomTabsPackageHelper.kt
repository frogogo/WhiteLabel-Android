package ru.poprobuy.poprobuy.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.browser.customtabs.CustomTabsService
import com.github.ajalt.timberkt.e

/**
 * Helper class for Custom Tabs.
 *
 * Borrowing from https://github.com/saschpe/android-customtabs
 */
object CustomTabsPackageHelper {

  private const val STABLE_PACKAGE = "com.android.chrome"
  private const val BETA_PACKAGE = "com.chrome.beta"
  private const val DEV_PACKAGE = "com.chrome.dev"
  private const val LOCAL_PACKAGE = "com.google.android.apps.chrome"

  private var packageNameToUse: String? = null

  /**
   * Goes through all apps that handle VIEW intents and have a warmup service. Picks
   * the one chosen by the user if there is one, otherwise makes a best effort to return a
   * valid package name.
   *
   * This is **not** threadsafe.
   *
   * @param context [Context] to use for accessing [PackageManager].
   * @return The package name recommended to use for connecting to custom tabs related components.
   */
  @JvmStatic
  fun getPackageNameToUse(context: Context): String? {
    if (packageNameToUse != null) {
      return packageNameToUse
    }
    val pm = context.packageManager

    // Get default VIEW intent handler.
    val activityIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"))
    val defaultHandlerInfo = pm.resolveActivity(activityIntent, 0)
    val defaultHandlerPackageName: String? = defaultHandlerInfo?.activityInfo?.packageName

    // Get all apps that can handle VIEW intents.
    val resolvedActivityList = pm.queryIntentActivities(activityIntent, 0)
    val packagesSupportingCustomTabs = mutableListOf<String>()
    resolvedActivityList.forEach { info ->
      val serviceIntent = Intent().apply {
        action = CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION
        setPackage(info.activityInfo.packageName)
      }

      pm.resolveService(serviceIntent, 0)?.let {
        packagesSupportingCustomTabs.add(info.activityInfo.packageName)
      }
    }

    // Now packagesSupportingCustomTabs contains all apps that can handle both VIEW intents
    // and service calls.
    packageNameToUse = when {
      packagesSupportingCustomTabs.isEmpty() -> null
      packagesSupportingCustomTabs.size == 1 -> packagesSupportingCustomTabs[0]
      !defaultHandlerPackageName.isNullOrEmpty() &&
          !hasSpecializedHandlerIntents(context, activityIntent) &&
          packagesSupportingCustomTabs.contains(defaultHandlerPackageName) -> defaultHandlerPackageName
      packagesSupportingCustomTabs.contains(STABLE_PACKAGE) -> STABLE_PACKAGE
      packagesSupportingCustomTabs.contains(BETA_PACKAGE) -> BETA_PACKAGE
      packagesSupportingCustomTabs.contains(DEV_PACKAGE) -> DEV_PACKAGE
      packagesSupportingCustomTabs.contains(LOCAL_PACKAGE) -> LOCAL_PACKAGE
      else -> null
    }
    return packageNameToUse
  }

  /**
   * Used to check whether there is a specialized handler for a given intent.
   *
   * @param intent The intent to check with.
   * @return Whether there is a specialized handler for the given intent.
   */
  private fun hasSpecializedHandlerIntents(context: Context, intent: Intent): Boolean {
    try {
      val pm = context.packageManager
      val handlers = pm.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER)
      if (handlers.size == 0) {
        return false
      }
      handlers.forEach { resolveInfo ->
        val filter = resolveInfo.filter ?: return@forEach
        if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) return@forEach
        if (resolveInfo.activityInfo == null) return@forEach
        return true
      }
    } catch (ex: RuntimeException) {
      e(ex) { "Runtime exception while getting specialized handlers" }
    }
    return false
  }
}
