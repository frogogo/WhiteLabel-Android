package ru.frogogo.whitelabel.util.moshi.adapter

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ru.frogogo.test.DataFixtures
import ru.frogogo.whitelabel.data.mapper.toResponse
import ru.frogogo.whitelabel.data.model.api.auth.AuthenticationResponseJson

internal class AuthenticationResponseAdapterTest {

  @Test
  fun fromJson() {
    val jsonObject = AuthenticationResponseJson(
      email = DataFixtures.USER_EMAIL,
      firstName = DataFixtures.USER_NAME,
      phoneNumber = DataFixtures.PHONE_NUMBER,
      jwt = DataFixtures.ACCESS_TOKEN,
      refreshToken = DataFixtures.REFRESH_TOKEN,
      isNew = false
    )

    AuthenticationResponseAdapter().fromJson(jsonObject) shouldBeEqualTo jsonObject.toResponse()
  }
}
