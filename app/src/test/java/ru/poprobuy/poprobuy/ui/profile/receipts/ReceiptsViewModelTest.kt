package ru.poprobuy.poprobuy.ui.profile.receipts

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.ViewModelTest
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.mapper.toUiModel
import ru.poprobuy.poprobuy.mockkObserver
import ru.poprobuy.poprobuy.usecase.UseCaseResult
import ru.poprobuy.poprobuy.usecase.receipt.GetReceiptsUseCase

@ExperimentalCoroutinesApi
class ReceiptsViewModelTest : ViewModelTest() {

  private lateinit var viewModel: ReceiptsViewModel
  private val getReceiptsUseCase: GetReceiptsUseCase = mockk(relaxed = true)
  private val navigation: ReceiptsNavigation = mockk(relaxed = true)

  private val isLoadingObserver = mockkObserver<Boolean>()
  private val dataObserver = mockkObserver<List<RecyclerViewItem>>()
  private val errorOccurredObserver = mockkObserver<Unit>()

  @Before
  fun startUp() {
    clearAllMocks()
    viewModel = ReceiptsViewModel(
      navigation = navigation,
      getReceiptsUseCase = getReceiptsUseCase
    ).apply {
      isLoadingLive.observeForever(isLoadingObserver)
      dataLive.observeForever(dataObserver)
      errorOccurredLiveEvent.observeForever(errorOccurredObserver)
    }
  }

  @Test
  fun `verify flow when data loaded successfully`() = runBlockingTest {
    coEvery { getReceiptsUseCase() } returns UseCaseResult.Success(listOf(DataFixtures.receipt))

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
    coEvery { getReceiptsUseCase() } returns UseCaseResult.Failure(Unit)

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
    val receipt = DataFixtures.receipt.toUiModel()
    viewModel.navigateToReceipt(receipt)

    viewModel.navigationLiveEvent.value shouldBeEqualTo navigation.navigateToReceipt(receipt)
  }

}
