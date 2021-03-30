package ru.poprobuy.poprobuy.view.dialog

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

interface DialogCompanion<T : DialogFragment> {

  fun T.showIn(fragmentManager: FragmentManager)
}
