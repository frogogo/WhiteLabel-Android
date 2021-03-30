package ru.poprobuy.poprobuy.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ru.poprobuy.test.DataFixtures
import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationResponse
import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationResponseJson

internal class AuthenticationMapperKtTest {

  @Test
  fun `mapper should convert json response to response`() {
    val response = DataFixtures.authenticationResponse
    val jsonResponse = AuthenticationResponseJson(
      email = response.user!!.email,
      firstName = response.user!!.firstName,
      phoneNumber = response.user!!.phoneNumber,
      refreshToken = response.refreshToken,
      jwt = response.accessToken,
      isNew = response.isNew
    )

    jsonResponse.toResponse() shouldBeEqualTo DataFixtures.authenticationResponse
  }

  @Test
  fun `mapper should return null user if email is null`() {
    val jsonResponse = AuthenticationResponseJson(
      email = null,
      firstName = "Alexey",
      phoneNumber = DataFixtures.PHONE_NUMBER,
      refreshToken = DataFixtures.REFRESH_TOKEN,
      jwt = DataFixtures.ACCESS_TOKEN,
      isNew = true
    )

    jsonResponse.toResponse() shouldBeEqualTo AuthenticationResponse(
      user = null,
      accessToken = DataFixtures.ACCESS_TOKEN,
      refreshToken = DataFixtures.REFRESH_TOKEN,
      isNew = true
    )
  }

  @Test
  fun `mapper should return null user if firstName is null`() {
    val jsonResponse = AuthenticationResponseJson(
      email = DataFixtures.USER_EMAIL,
      firstName = null,
      phoneNumber = DataFixtures.PHONE_NUMBER,
      refreshToken = DataFixtures.REFRESH_TOKEN,
      jwt = DataFixtures.ACCESS_TOKEN,
      isNew = true
    )

    jsonResponse.toResponse() shouldBeEqualTo AuthenticationResponse(
      user = null,
      accessToken = DataFixtures.ACCESS_TOKEN,
      refreshToken = DataFixtures.REFRESH_TOKEN,
      isNew = true
    )
  }
}
