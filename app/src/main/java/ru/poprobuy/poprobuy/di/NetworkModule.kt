@file:Suppress("USELESS_CAST")

package ru.poprobuy.poprobuy.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Authenticator
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
import ru.poprobuy.poprobuy.data.network.interceptor.*
import ru.poprobuy.poprobuy.util.Constants
import ru.poprobuy.poprobuy.util.Constants.POPROBUY_API_ENDPOINT
import ru.poprobuy.poprobuy.util.moshi.MoshiUtils
import ru.poprobuy.poprobuy.util.network.UserAgentFactory
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
  single { TokenAuthenticator() as Authenticator }

  // Network
  single {
    createHttpClient(
      context = androidContext(),
      authorizationInterceptor = get(),
      authenticator = get(),
      userAgent = UserAgentFactory.create()
    )
  }
  single {
    createRetrofit(
      client = get(),
      url = POPROBUY_API_ENDPOINT
    )
  }
  single { getApi<PoprobuyApi>(get()) }

  // Util
  single { AutoLogoutNotifier(get()) }
}

fun createHttpClient(
  context: Context,
  authorizationInterceptor: Interceptor,
  authenticator: Authenticator,
  userAgent: String,
): OkHttpClient {
  return OkHttpClient.Builder().apply {
    // Timeout settings
    connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
    readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
    writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)

    // Interceptors
    @Suppress("ConstantConditionIf")
    if (BuildConfig.DEBUG) {
      addInterceptor(ChuckerInterceptor.Builder(context).build())
      addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
      })
    }
    addInterceptor(UserAgentInterceptor(userAgent))
    addInterceptor(AcceptInterceptor())
    addInterceptor(AcceptLanguageInterceptor())
    addInterceptor(ApiVersionInterceptor(Constants.POPROBUY_API_VERSION))
    addInterceptor(NoContentInterceptor())

    // Auth
    authenticator(authenticator)
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
  return MoshiConverterFactory.create(MoshiUtils.getNetworkAdapter())
}
