package ru.frogogo.whitelabel.extension

import androidx.annotation.MainThread
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.frogogo.whitelabel.core.Event
import ru.frogogo.whitelabel.core.observeEvent

inline fun Fragment.alert(dialog: MaterialAlertDialogBuilder.() -> Unit): AlertDialog {
  return MaterialAlertDialogBuilder(requireContext()).apply(dialog).show()
}

@MainThread
fun <T> Fragment.observe(liveData: LiveData<T>, onChanged: (T) -> Unit) {
  liveData.observe(viewLifecycleOwner, onChanged)
}

@MainThread
inline fun <T> Fragment.observeEvent(liveData: LiveData<Event<T>>, crossinline onChanged: (T) -> Unit) {
  liveData.observeEvent(owner = viewLifecycleOwner, onChanged = onChanged)
}
