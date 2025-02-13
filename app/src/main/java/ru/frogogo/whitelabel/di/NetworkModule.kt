@file:Suppress("USELESS_CAST")

package ru.frogogo.whitelabel.di

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
import retrofit2.create
import ru.frogogo.whitelabel.BuildConfig
import ru.frogogo.whitelabel.data.network.FrogogoApi
import ru.frogogo.whitelabel.data.network.interceptor.AcceptInterceptor
import ru.frogogo.whitelabel.data.network.interceptor.AcceptLanguageInterceptor
import ru.frogogo.whitelabel.data.network.interceptor.ApiVersionInterceptor
import ru.frogogo.whitelabel.data.network.interceptor.AuthInterceptor
import ru.frogogo.whitelabel.data.network.interceptor.AutoLogoutNotifier
import ru.frogogo.whitelabel.data.network.interceptor.NoContentInterceptor
import ru.frogogo.whitelabel.data.network.interceptor.TokenAuthenticator
import ru.frogogo.whitelabel.data.network.interceptor.UserAgentInterceptor
import ru.frogogo.whitelabel.util.Constants
import ru.frogogo.whitelabel.util.Constants.FROGOGO_API_ENDPOINT
import ru.frogogo.whitelabel.util.NetworkConstants.CACHE_DIRECTORY
import ru.frogogo.whitelabel.util.NetworkConstants.CACHE_SIZE_BYTES
import ru.frogogo.whitelabel.util.NetworkConstants.TIMEOUT_CONNECT
import ru.frogogo.whitelabel.util.NetworkConstants.TIMEOUT_READ
import ru.frogogo.whitelabel.util.NetworkConstants.TIMEOUT_WRITE
import ru.frogogo.whitelabel.util.moshi.MoshiUtils
import ru.frogogo.whitelabel.util.network.UserAgentFactory
import java.io.File
import java.util.concurrent.TimeUnit

val networkModule = module {
  // Interceptors
  single { AuthInterceptor(get()) as Interceptor }
  single { TokenAuthenticator() as Authenticator }

  // Network
  single {
    createHttpClient(
      context = androidContext(),
      authenticator = get(),
      authorizationInterceptor = get(),
      userAgent = UserAgentFactory.create(),
    )
  }
  single {
    createRetrofit(
      client = get(),
      url = FROGOGO_API_ENDPOINT,
    )
  }
  single { get<Retrofit>().create<FrogogoApi>() }

  // Util
  single { AutoLogoutNotifier(get()) }
}

private fun createHttpClient(
  context: Context,
  authenticator: Authenticator,
  authorizationInterceptor: Interceptor,
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
      addInterceptor(
        HttpLoggingInterceptor().apply {
          level = HttpLoggingInterceptor.Level.HEADERS
        },
      )
    }
    addInterceptor(UserAgentInterceptor(userAgent))
    addInterceptor(AcceptInterceptor())
    addInterceptor(AcceptLanguageInterceptor())
    addInterceptor(ApiVersionInterceptor(Constants.FROGOGO_API_VERSION))
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

private fun createMoshiConverterFactory(): Converter.Factory {
  return MoshiConverterFactory.create(MoshiUtils.getNetworkAdapter())
}
