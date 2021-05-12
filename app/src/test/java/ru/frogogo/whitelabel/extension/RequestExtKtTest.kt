package ru.frogogo.whitelabel.extension

import okhttp3.Request
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

internal class RequestExtKtTest {

  @Test
  fun `addAuthHeader should add auth header with default scheme`() {
    val request = Request.Builder().apply {
      url(STUB_URL)
      addAuthHeader(TOKEN)
    }.build()

    request.header(AUTHORIZATION_HEADER) shouldBeEqualTo "$BEARER_SCHEME $TOKEN"
  }

  @Test
  fun `addAuthHeader should add auth header with provided scheme`() {
    val request = Request.Builder().apply {
      url(STUB_URL)
      addAuthHeader(scheme = CUSTOM_SCHEME, token = TOKEN)
    }.build()

    request.header(AUTHORIZATION_HEADER) shouldBeEqualTo "$CUSTOM_SCHEME $TOKEN"
  }

  @Test
  fun `addAuthHeader should override existing header`() {
    val request = Request.Builder().apply {
      url(STUB_URL)
      header(AUTHORIZATION_HEADER, "$CUSTOM_SCHEME old_token")
      addAuthHeader(TOKEN)
    }.build()

    request.header(AUTHORIZATION_HEADER) shouldBeEqualTo "$BEARER_SCHEME $TOKEN"
  }

  companion object {
    private const val STUB_URL = "https://google.com"
    private const val AUTHORIZATION_HEADER = "Authorization"

    private const val BEARER_SCHEME = "Bearer"
    private const val CUSTOM_SCHEME = "Token"

    private const val TOKEN = "auth_token"
  }
}
