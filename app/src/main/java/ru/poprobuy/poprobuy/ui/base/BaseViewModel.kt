package ru.poprobuy.poprobuy.ui.base

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

  open fun onCreate() = Unit

  open fun onStart() = Unit

}
