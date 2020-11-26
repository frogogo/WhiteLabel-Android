package ru.poprobuy.poprobuy.ui.scanner

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockk
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test
import ru.poprobuy.poprobuy.dictionary.ScanMode
import ru.poprobuy.poprobuy.usecase.receipt.CreateReceiptUseCase
import ru.poprobuy.poprobuy.usecase.vending_machine.AssignVendingMachineUseCase
import ru.poprobuy.poprobuy.util.ResourceProvider

class ScannerViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private val createReceiptUseCase: CreateReceiptUseCase = mockk(relaxed = true)
  private val assignVendingMachineUseCase: AssignVendingMachineUseCase = mockk(relaxed = true)
  private val resourceProvider: ResourceProvider = mockk()

  @Test
  fun `view model navigates to machine help`() {
    val navigation = mockk<ScannerNavigation>(relaxed = true)
    val viewModel = ScannerViewModel(
      scanMode = ScanMode.MACHINE,
      receiptId = 1,
      navigation = navigation,
      createReceiptUseCase = createReceiptUseCase,
      assignVendingMachineUseCase = assignVendingMachineUseCase,
      resourceProvider = resourceProvider
    )

    viewModel.navigateToHelp()

    viewModel.navigationLiveEvent.value shouldBeEqualTo navigation.navigateToMachineHelp()
  }

  @Test
  fun `view model navigates to receipt help`() {
    val navigation = mockk<ScannerNavigation>(relaxed = true)
    val viewModel = ScannerViewModel(
      scanMode = ScanMode.RECEIPT,
      receiptId = -1,
      navigation = navigation,
      createReceiptUseCase = createReceiptUseCase,
      assignVendingMachineUseCase = assignVendingMachineUseCase,
      resourceProvider = resourceProvider
    )

    viewModel.navigateToHelp()

    viewModel.navigationLiveEvent.value shouldBeEqualTo navigation.navigateToReceiptHelp()
  }

  @Test
  fun `view model navigates to manual machine enter`() {
    val navigation = mockk<ScannerNavigation>(relaxed = true)
    val viewModel = ScannerViewModel(
      scanMode = ScanMode.MACHINE,
      receiptId = 1,
      navigation = navigation,
      createReceiptUseCase = createReceiptUseCase,
      assignVendingMachineUseCase = assignVendingMachineUseCase,
      resourceProvider = resourceProvider
    )

    viewModel.navigateToManualMachineEnter()

    viewModel.navigationLiveEvent.value shouldBeEqualTo navigation.navigateToManualMachineEnter(1)
  }

}
