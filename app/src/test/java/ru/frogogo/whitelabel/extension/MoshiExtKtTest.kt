package ru.frogogo.whitelabel.extension

import com.squareup.moshi.Moshi
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.frogogo.whitelabel.data.model.api.user.User
import ru.frogogo.whitelabel.data.model.api.user.UserJsonAdapter

class MoshiExtKtTest {

  private val moshi = Moshi.Builder().build()

  @Test
  fun `fromJsonOrNull returns object if json is correct`() {
    val expected = User(firstName = "First", email = "mail@mail.ru", phoneNumber = "+7999112233")

    UserJsonAdapter(moshi).fromJsonOrNull(JSON_CORRECT) shouldBeEqualTo expected
  }

  @Test
  fun `fromJson throws exception if can't parse json top object`() {
    assertThrows<Exception> {
      moshi.fromJson<User>(JSON_EMPTY)
    }
  }

  @Test
  fun `fromJson throws exception if json is corrupted`() {
    assertThrows<Exception> {
      moshi.fromJson<User>(JSON_CORRUPTED)
    }
  }

  @Test
  fun `fromJson returns object if json string is correct`() {
    val expected = User(firstName = "First", email = "mail@mail.ru", phoneNumber = "+7999112233")

    moshi.fromJson<User>(JSON_CORRECT) shouldBeEqualTo expected
  }

  companion object {
    private const val JSON_EMPTY = ""
    private val JSON_CORRUPTED = """
      {
        "email": "mail@mail.ru",
        "first_name": "First",
        "corrupted_field": "+7999112233"
      }
    """.trimIndent()
    private val JSON_CORRECT = """
      {
        "email": "mail@mail.ru",
        "first_name": "First",
        "phone_number": "+7999112233"
      }
    """.trimIndent()
  }
}
