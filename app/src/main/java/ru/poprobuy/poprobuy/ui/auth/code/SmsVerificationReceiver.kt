package ru.poprobuy.poprobuy.ui.auth.code

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.github.ajalt.timberkt.e
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import ru.poprobuy.poprobuy.extension.hideKeyboard

class SmsVerificationReceiver(
  private val activityRequestCode: Int,
  private val fragmentRetriever: () -> Fragment,
) : BroadcastReceiver() {

  override fun onReceive(context: Context, intent: Intent) {
    if (intent.action != SmsRetriever.SMS_RETRIEVED_ACTION) return

    val extras = intent.extras
    val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

    when (smsRetrieverStatus.statusCode) {
      CommonStatusCodes.SUCCESS -> {
        val consentIntent = extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
        try {
          val fragment = fragmentRetriever.invoke()
          // Hide keyboard to make activity appearance more smooth
          fragment.activity?.hideKeyboard()
          // Show Consent Activity
          fragment.startActivityForResult(consentIntent, activityRequestCode)
        } catch (ex: ActivityNotFoundException) {
          e(ex) { "Error while trying to start sms consent activity " }
        }
      }
      CommonStatusCodes.TIMEOUT -> {
        // Do nothing
      }
    }
  }
}
