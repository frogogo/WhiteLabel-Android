package ru.poprobuy.poprobuy.util

import android.content.Context
import coil.ImageLoader
import coil.util.CoilUtils
import coil.util.DebugLogger
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import ru.poprobuy.poprobuy.BuildConfig
import ru.poprobuy.poprobuy.extension.getActivityManager

object CoilImageLoaderFactory {

  fun create(context: Context): ImageLoader = ImageLoader.Builder(context)
    .crossfade(true) // Show a short crossfade when loading images from network or disk.
    .allowRgb565(context.getActivityManager().isLowRamDevice)
    .okHttpClient { createHttpClient(context) }
    .addLogger()
    .build()

  private fun createHttpClient(context: Context): OkHttpClient {
    val dispatcher = Dispatcher().apply {
      maxRequestsPerHost = maxRequests
    }

    return OkHttpClient.Builder()
      .cache(CoilUtils.createDefaultCache(context))
      .dispatcher(dispatcher)
      .build()
  }

  private fun ImageLoader.Builder.addLogger(): ImageLoader.Builder {
    // Enable logging to the standard Android log if this is a debug build.
    if (BuildConfig.DEBUG) {
      logger(DebugLogger())
    }

    return this
  }

}
