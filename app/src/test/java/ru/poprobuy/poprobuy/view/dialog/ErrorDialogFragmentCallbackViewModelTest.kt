package ru.poprobuy.poprobuy.view.dialog

import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.poprobuy.test.base.ViewModelTestJUnit5
import ru.poprobuy.poprobuy.core.Event
import ru.poprobuy.test.mockkObserver

@ExperimentalCoroutinesApi
internal class ErrorDialogFragmentCallbackViewModelTest : ViewModelTestJUnit5() {

  private lateinit var viewModel: ErrorDialogFragmentCallbackViewModel

  private val onDismissEventObserver = mockkObserver<Event<Int>>()

  @BeforeEach
  fun startUp() {
    viewModel = ErrorDialogFragmentCallbackViewModel().apply {
      onDismissEvent.observeForever(onDismissEventObserver)
    }
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `viewModel should send onDismiss event with proper dialogId`() {
    viewModel.onDismiss(43)

    verifySequence {
      onDismissEventObserver.onChanged(Event(43))
    }
    confirmVerified()
  }

  private fun confirmVerified() {
    confirmVerified(onDismissEventObserver)
  }
}
