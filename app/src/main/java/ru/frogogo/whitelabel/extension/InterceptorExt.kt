package ru.frogogo.whitelabel.extension

import okhttp3.Interceptor
import retrofit2.Invocation
import kotlin.reflect.KClass

fun <T : Annotation> Interceptor.Chain.getAnnotation(
  annotationClass: KClass<T>,
): T? = request()
  .tag(Invocation::class.java)?.run {
    method().getAnnotation(annotationClass.java)
  }
