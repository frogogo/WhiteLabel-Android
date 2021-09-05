package ru.frogogo.whitelabel.analytics

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import ru.frogogo.whitelabel.util.analytics.AnalyticsEvent
import java.util.Locale

object SystemEvents {

  object AutoLogout : AnalyticsEvent("system_auto_logout")

  data class TokenRefreshWork(val result: Result) : AnalyticsEvent("system_token_refresh_work") {

    override fun getEventParameters(): Bundle = bundleOf(
      PARAM_RESULT to result.name.lowercase(Locale.ENGLISH),
    )

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
