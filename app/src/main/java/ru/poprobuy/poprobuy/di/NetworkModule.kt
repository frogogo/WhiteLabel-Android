package ru.poprobuy.poprobuy.di

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.poprobuy.poprobuy.BuildConfig
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.data.network.interceptor.AuthInterceptor
import ru.poprobuy.poprobuy.data.network.interceptor.NoContentInterceptor
import ru.poprobuy.poprobuy.data.network.interceptor.PoprobuyInterceptor
import ru.poprobuy.poprobuy.util.Constants.POPROBUY_API_ENDPOINT
import java.io.File
import java.util.concurrent.TimeUnit

private const val TIMEOUT_CONNECT = 60L
private const val TIMEOUT_READ = 60L
private const val TIMEOUT_WRITE = 60L

private const val CACHE_SIZE_BYTES: Long = 1024 * 1024 * 10 // 10 MB
private const val CACHE_DIRECTORY = "network"

val networkModule = module {
  // Interceptors
  single { AuthInterceptor(get()) as Interceptor }

  // Network
  single {
    createHttpClient(
      context = androidContext(),
      authorizationInterceptor = get()
    )
  }
  single {
    createRetrofit(
      client = get(),
      url = POPROBUY_API_ENDPOINT
    )
  }
  single { getApi<PoprobuyApi>(get()) }
}

fun createHttpClient(
  context: Context,
  authorizationInterceptor: Interceptor
): OkHttpClient {
  return OkHttpClient.Builder().apply {
    // Timeout settings
    connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
    readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
    writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)

    // Interceptors
    @Suppress("ConstantConditionIf")
    if (BuildConfig.DEBUG) {
      addInterceptor(ChuckInterceptor(context))
      addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
      })
    }
    addInterceptor(PoprobuyInterceptor())
    addInterceptor(NoContentInterceptor())
    addInterceptor(authorizationInterceptor)

    // Cache
    cache(Cache(File("${context.cacheDir.path}/$CACHE_DIRECTORY"), CACHE_SIZE_BYTES))

  }.build()
}

private fun createRetrofit(client: OkHttpClient, url: String): Retrofit {
  return Retrofit.Builder()
    .baseUrl(url)
    .client(client)
    .addConverterFactory(createMoshiConverterFactory())
    .build()
}

private inline fun <reified T> getApi(retrofit: Retrofit): T = retrofit.create(T::class.java)

private fun createMoshiConverterFactory(): Converter.Factory {
  return MoshiConverterFactory.create()
}
