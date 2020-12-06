package ru.poprobuy.poprobuy.extension

import androidx.annotation.MainThread
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import ru.poprobuy.poprobuy.core.Event
import ru.poprobuy.poprobuy.core.observeEvent

typealias Func = () -> Unit
typealias RationaleShouldBeShown = (PermissionToken) -> Unit

fun Fragment.withPermission(
  permission: String,
  onShowRationale: RationaleShouldBeShown = { it.continuePermissionRequest() },
  onPermissionDenied: Func? = null,
  onNeverAskAgain: Func? = null,
  onPermissionGranted: Func,
) {
  val listener = object : PermissionListener {
    override fun onPermissionGranted(response: PermissionGrantedResponse) {
      onPermissionGranted()
    }

    override fun onPermissionRationaleShouldBeShown(request: PermissionRequest, token: PermissionToken) {
      onShowRationale.invoke(token)
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse) {
      if (response.isPermanentlyDenied) {
        onNeverAskAgain?.invoke()
      } else {
        onPermissionDenied?.invoke()
      }
    }
  }

  Dexter.withContext(requireContext())
    .withPermission(permission)
    .withListener(listener)
    .onSameThread()
    .check()
}

inline fun Fragment.alert(dialog: MaterialAlertDialogBuilder.() -> Unit): AlertDialog {
  return MaterialAlertDialogBuilder(requireContext()).apply(dialog).show()
}

@MainThread
inline fun <T> Fragment.observe(liveData: LiveData<T>, crossinline onChanged: (T) -> Unit) {
  liveData.observe(owner = viewLifecycleOwner, onChanged = onChanged)
}

@MainThread
inline fun <T> Fragment.observeEvent(liveData: LiveData<Event<T>>, crossinline onChanged: (T) -> Unit) {
  liveData.observeEvent(owner = viewLifecycleOwner, onChanged = onChanged)
}
