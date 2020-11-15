package ru.poprobuy.poprobuy.ui.scanner

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockk
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test
import ru.poprobuy.poprobuy.dictionary.ScanMode
import ru.poprobuy.poprobuy.usecase.receipt.CreateReceiptUseCase
import ru.poprobuy.poprobuy.util.ResourceProvider

class ScannerViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private val createReceiptUseCase: CreateReceiptUseCase = mockk(relaxed = true)
  private val resourceProvider: ResourceProvider = mockk()

  @Test
  fun `view model navigates to machine help`() {
    val navigation = mockk<ScannerNavigation>(relaxed = true)
    val viewModel = ScannerViewModel(ScanMode.MACHINE, 1, navigation, createReceiptUseCase, resourceProvider)

    viewModel.navigateToHelp()

    viewModel.navigationLiveEvent.value shouldBeEqualTo navigation.navigateToMachineHelp()
  }

  @Test
  fun `view model navigates to receipt help`() {
    val navigation = mockk<ScannerNavigation>(relaxed = true)
    val viewModel = ScannerViewModel(ScanMode.RECEIPT, -1, navigation, createReceiptUseCase, resourceProvider)

    viewModel.navigateToHelp()

    viewModel.navigationLiveEvent.value shouldBeEqualTo navigation.navigateToReceiptHelp()
  }

  @Test
  fun `view model navigates to manual machine enter`() {
    val navigation = mockk<ScannerNavigation>(relaxed = true)
    val viewModel = ScannerViewModel(ScanMode.MACHINE, 1, navigation, createReceiptUseCase, resourceProvider)

    viewModel.navigateToManualMachineEnter()

    viewModel.navigationLiveEvent.value shouldBeEqualTo navigation.navigateToManualMachineEnter(1)
  }

}
