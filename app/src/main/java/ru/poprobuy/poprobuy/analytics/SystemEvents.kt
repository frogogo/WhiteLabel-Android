package ru.poprobuy.poprobuy.analytics

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import ru.poprobuy.poprobuy.util.analytics.AnalyticsEvent
import java.util.*

object SystemEvents {

  object AutoLogout : AnalyticsEvent("system_auto_logout")

  data class TokenRefreshWork(val result: Result) : AnalyticsEvent("system_token_refresh_work") {

    override fun getEventParameters(): Bundle? =
      bundleOf(PARAM_RESULT to result.name.toLowerCase(Locale.ENGLISH))

    companion object {
      @VisibleForTesting
      const val PARAM_RESULT = "result"
    }

    enum class Result {
      SKIPPED,
      REFRESHED,
      FAILED
    }
  }

}
