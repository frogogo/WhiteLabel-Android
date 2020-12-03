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
import ru.poprobuy.poprobuy.util.NetworkConstants.CACHE_DIRECTORY
import ru.poprobuy.poprobuy.util.NetworkConstants.CACHE_SIZE_BYTES
import ru.poprobuy.poprobuy.util.NetworkConstants.TIMEOUT_CONNECT
import ru.poprobuy.poprobuy.util.NetworkConstants.TIMEOUT_READ
import ru.poprobuy.poprobuy.util.NetworkConstants.TIMEOUT_WRITE
import ru.poprobuy.poprobuy.util.moshi.MoshiUtils
import ru.poprobuy.poprobuy.util.network.UserAgentFactory
import java.io.File
import java.util.concurrent.TimeUnit

val networkModule = module {
  // Interceptors
  single { AuthInterceptor(get()) as Interceptor }
  single { TokenAuthenticator() as Authenticator }
  single { MachineSessionIdTakeInterceptor(get()) }
  single { MachineSessionIdProviderInterceptor(get()) }

  // Network
  single {
    createHttpClient(
      context = androidContext(),
      authenticator = get(),
      authorizationInterceptor = get(),
      machineSessionIdTakeInterceptor = get(),
      machineSessionIdProviderInterceptor = get(),
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
  authenticator: Authenticator,
  authorizationInterceptor: Interceptor,
  machineSessionIdTakeInterceptor: MachineSessionIdTakeInterceptor,
  machineSessionIdProviderInterceptor: MachineSessionIdProviderInterceptor,
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
    addInterceptor(machineSessionIdTakeInterceptor)
    addInterceptor(machineSessionIdProviderInterceptor)

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
