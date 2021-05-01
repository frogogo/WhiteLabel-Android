package ru.frogogo.whitelabel.extension.binding

import android.content.Context
import androidx.viewbinding.ViewBinding

val ViewBinding.context: Context
  get() = root.context
