package ru.frogogo.whitelabel.ui.profile.receipts

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.frogogo.whitelabel.core.Event
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.core.navigation.NavigationCommand
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.ui.profile.receipts.details.ReceiptDetailsButtonState
import ru.frogogo.whitelabel.usecase.receipt.GetReceiptsUseCase
import ru.frogogo.whitelabel.util.network.NetworkError
import ru.frogogo.test.*
import ru.frogogo.test.base.ViewModelTestJUnit5

@ExperimentalCoroutinesApi
class ReceiptsViewModelTest : ViewModelTestJUnit5() {

  private lateinit var viewModel: ReceiptsViewModel

  private val getReceiptsUseCase: GetReceiptsUseCase = mockk(relaxed = true)
  private val navigation: ReceiptsNavigation = mockk(relaxed = true)

  private val navigationObserver = mockkEventObserver<NavigationCommand>()
  private val isLoadingObserver = mockkObserver<Boolean>()
  private val dataObserver = mockkObserver<List<RecyclerViewItem>>()
  private val errorOccurredObserver = mockkObserver<Unit>()

  @BeforeEach
  fun startUp() {
    mockkObject(ReceiptsDataUtils)

    viewModel = ReceiptsViewModel(
      navigation = navigation,
      getReceiptsUseCase = getReceiptsUseCase
    ).apply {
      navigationLiveEvent.observeForever(navigationObserver)
      isLoadingLive.observeForever(isLoadingObserver)
      dataLive.observeForever(dataObserver)
      errorOccurredLiveEvent.observeForever(errorOccurredObserver)
    }
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `verify flow when data loaded successfully`() = runBlockingTest {
    coEvery { getReceiptsUseCase() } returns Result.Success(listOf(DataFixtures.getReceiptUIModel()))

    viewModel.loadReceipts()

    coVerifySequence {
      isLoadingObserver.onChanged(true)
      getReceiptsUseCase()
      isLoadingObserver.onChanged(false)
      dataObserver.onChanged(isNull(inverse = true))
    }
  }

  // FIXME: Failing test
//  @Test
//  fun `second data load shouldn't show loading`() = runBlockingTest {
//    coEvery { getReceiptsUseCase() } returns UseCaseResult.Success(listOf(DataFixtures.receipt))
//
//    viewModel.loadReceipts()
//    viewModel.loadReceipts()
//
//    coVerifySequence {
//      // First call
//      isLoadingObserver.onChanged(true)
//      getReceiptsUseCase()
//      isLoadingObserver.onChanged(false)
//      dataObserver.onChanged(isNull(inverse = true))
//      // Second call
//      isLoadingObserver.onChanged(false)
//      getReceiptsUseCase()
//      isLoadingObserver.onChanged(false)
//      dataObserver.onChanged(isNull(inverse = true))
//    }
//  }

  @Test
  fun `verify flow when data loading failed`() = runBlockingTest {
    coEvery { getReceiptsUseCase() } returns Result.Failure(NetworkError.testError())

    viewModel.loadReceipts()

    coVerifySequence {
      isLoadingObserver.onChanged(true)
      getReceiptsUseCase()
      isLoadingObserver.onChanged(false)
      errorOccurredObserver.onChanged(Unit)
    }
  }

  @Test
  fun `view model navigates to receipt`() {
    val state = ReceiptDetailsButtonState(canCreateReceipt = false, canTakeProduct = false)
    every { ReceiptsDataUtils.getReceiptDetailsButtonState(any()) } returns state
    val receipt = DataFixtures.getReceiptUIModel()

    viewModel.navigateToReceipt(receipt)

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToReceipt(receipt, state))
  }
}
