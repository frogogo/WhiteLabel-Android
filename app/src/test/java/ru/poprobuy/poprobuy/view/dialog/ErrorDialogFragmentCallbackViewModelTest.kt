package ru.poprobuy.poprobuy.view.dialog

import io.mockk.confirmVerified
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.ViewModelTest
import ru.poprobuy.poprobuy.mockkObserver
import ru.poprobuy.poprobuy.util.Event

@ExperimentalCoroutinesApi
internal class ErrorDialogFragmentCallbackViewModelTest : ViewModelTest() {

  private lateinit var viewModel: ErrorDialogFragmentCallbackViewModel

  private val onDismissEventObserver = mockkObserver<Event<Int>>()

  @Before
  fun startUp() {
    viewModel = ErrorDialogFragmentCallbackViewModel().apply {
      onDismissEvent.observeForever(onDismissEventObserver)
    }
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
