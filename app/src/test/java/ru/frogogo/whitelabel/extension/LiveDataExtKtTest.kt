package ru.frogogo.whitelabel.extension

import androidx.lifecycle.MutableLiveData
import io.mockk.every
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import ru.frogogo.test.rule.InstantTaskExecutorExtension
import ru.frogogo.test.mockkObserver

class LiveDataExtKtTest {

  @Suppress("unused")
  @JvmField
  @RegisterExtension
  val instantExecutorExtension = InstantTaskExecutorExtension()

  @Test
  fun `converted live data delivers values`() {
    val mutableLiveData = MutableLiveData<Int>()
    val observableData = mutableLiveData.asLiveData()
    val observer = mockkObserver<Int>()
    observableData.observeForever(observer)

    mutableLiveData.postValue(1)

    every {
      observer.onChanged(1)
    }

    observableData.value shouldBeEqualTo 1
  }

  @Test
  fun `extension checks live data emptiness`() {
    val mutableLiveData = MutableLiveData<Int>()

    mutableLiveData.isEmpty().shouldBeTrue()
  }

  @Test
  fun `extension checks filled live data emptiness`() {
    val mutableLiveData = MutableLiveData<Int>()
    mutableLiveData.postValue(1)

    mutableLiveData.isEmpty().shouldBeFalse()
  }
}
