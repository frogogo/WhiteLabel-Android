package ru.poprobuy.poprobuy.di

import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

typealias Func = () -> Unit
typealias RationaleShouldBeShown = (PermissionToken) -> Unit

fun Fragment.withPermission(
  permission: String,
  onShowRationale: RationaleShouldBeShown = { it.continuePermissionRequest() },
  onPermissionDenied: Func? = null,
  onNeverAskAgain: Func? = null,
  onPermissionGranted: Func
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

fun Fragment.requestApplyInsets() = ViewCompat.requestApplyInsets(requireView())
