package ru.frogogo.test.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import ru.frogogo.test.rule.CoroutinesTestRule

@ExperimentalCoroutinesApi
open class ViewModelTestJUnit4 {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineTestRule = CoroutinesTestRule()
}
