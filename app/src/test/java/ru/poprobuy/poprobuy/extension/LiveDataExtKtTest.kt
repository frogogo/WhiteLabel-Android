package ru.poprobuy.poprobuy.extension

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import io.mockk.every
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test
import ru.poprobuy.poprobuy.mockkObserver

class LiveDataExtKtTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

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

}
