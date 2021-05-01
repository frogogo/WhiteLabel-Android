package ru.frogogo.whitelabel.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.github.ajalt.timberkt.d
import java.lang.reflect.Method
import kotlin.reflect.KClass

private val inflateMethodsCache = mutableMapOf<Class<out ViewBinding>, Method>()

inline fun <reified VB : ViewBinding> ViewGroup.inflateViewBinding(
  context: Context = this.context,
  attachToRoot: Boolean = true,
): VB {
  return VB::class.inflate(LayoutInflater.from(context), this, attachToRoot)
}

fun <VB : ViewBinding> KClass<VB>.inflate(
  inflater: LayoutInflater,
  root: ViewGroup?,
  attachToRoot: Boolean,
): VB {
  val inflateMethod = java.getInflateMethod()
  @Suppress("UNCHECKED_CAST")
  return if (inflateMethod.parameterTypes.size > 2) {
    inflateMethod.invoke(null, inflater, root, attachToRoot)
  } else {
    if (!attachToRoot) d { "attachToRoot is always true for ${java.simpleName}.inflate" }
    inflateMethod.invoke(null, inflater, root)
  } as VB
}

private fun Class<out ViewBinding>.getInflateMethod(): Method {
  return inflateMethodsCache.getOrPut(this) {
    declaredMethods.find { method ->
      val parameterTypes = method.parameterTypes
      method.name == "inflate" &&
          parameterTypes[0] == LayoutInflater::class.java &&
          parameterTypes.getOrNull(1) == ViewGroup::class.java &&
          (parameterTypes.size == 2 || parameterTypes[2] == Boolean::class.javaPrimitiveType)
    } ?: error("Method ${this.simpleName}.inflate(LayoutInflater, ViewGroup[, boolean]) not found.")
  }
}
