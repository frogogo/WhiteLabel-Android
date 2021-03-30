package ru.poprobuy.test.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import ru.poprobuy.test.rule.CoroutinesTestRule

@ExperimentalCoroutinesApi
open class ViewModelTestJUnit4 {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineTestRule = CoroutinesTestRule()
}
