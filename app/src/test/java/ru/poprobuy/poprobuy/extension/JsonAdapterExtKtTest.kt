package ru.poprobuy.poprobuy.extension

import com.squareup.moshi.Moshi
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Test
import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.data.model.api.user.UserJsonAdapter

class JsonAdapterExtKtTest {

  private val moshi = Moshi.Builder().build()

  @Test
  fun `fromJsonOrNull returns null if json is empty`() {
    UserJsonAdapter(moshi).fromJsonOrNull("").shouldBeNull()
  }

  @Test
  fun `fromJsonOrNull returns null if json is corrupted`() {
    val json = """
      {
      "email": "mail@mail.ru",
      "first_name": "First",
      "corrupted_field": "+7999112233"
      }
    """.trimIndent()

    UserJsonAdapter(moshi).fromJsonOrNull(json).shouldBeNull()
  }

  @Test
  fun `fromJsonOrNull returns object if json is correct`() {
    val json = """
      {
      "email": "mail@mail.ru",
      "first_name": "First",
      "phone_number": "+7999112233"
      }
    """.trimIndent()

    val expected = User(firstName = "First", email = "mail@mail.ru", phoneNumber = "+7999112233")

    UserJsonAdapter(moshi).fromJsonOrNull(json) shouldBeEqualTo expected
  }

}
