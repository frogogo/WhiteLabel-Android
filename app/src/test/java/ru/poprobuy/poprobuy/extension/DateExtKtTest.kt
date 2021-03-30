package ru.poprobuy.poprobuy.extension

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ru.poprobuy.test.toDate
import java.time.LocalDate
import java.time.LocalDateTime

internal class DateExtKtTest {

  @Test
  fun toDateTime() {
    val date = LocalDateTime.of(2020, 10, 5, 10, 20).toDate()

    date.toDateTime() shouldBeEqualTo "05.10.2020 - 10:20"
  }

  @Test
  fun toSimpleDate() {
    val date = LocalDate.of(2020, 10, 5).toDate()

    date.toSimpleDate() shouldBeEqualTo "05.10.2020"
  }
}
