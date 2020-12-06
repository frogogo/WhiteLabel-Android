package ru.poprobuy.poprobuy.view.dialog

import org.amshove.kluent.shouldHaveSize
import org.junit.jupiter.api.Test

internal class ErrorDialogFragmentTest {

  @Test
  fun `dialog ids should be different`() {
    val ids = listOf(Caller1.ERROR_DIALOG_ID, Caller2.ERROR_DIALOG_ID, Caller3.ERROR_DIALOG_ID)
    ids.distinct().shouldHaveSize(3)
  }

  object Caller1 {
    val ERROR_DIALOG_ID = ErrorDialogFragment.getDialogId()
  }

  object Caller2 {
    val ERROR_DIALOG_ID = ErrorDialogFragment.getDialogId()
  }

  object Caller3 {
    val ERROR_DIALOG_ID = ErrorDialogFragment.getDialogId()
  }

}
