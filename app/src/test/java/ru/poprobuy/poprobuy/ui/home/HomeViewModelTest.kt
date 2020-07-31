package ru.poprobuy.poprobuy.ui.home

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
import ru.poprobuy.poprobuy.usecase.home.GetHomeUseCase

@ExperimentalCoroutinesApi
class HomeViewModelTest : ViewModelTest() {

  private lateinit var viewModel: HomeViewModel
  private val navigation: HomeNavigation = mockk(relaxed = true)
  private val getHomeUseCase: GetHomeUseCase = mockk(relaxed = true)

  private val isLoadingObserver = mockkObserver<Boolean>()
  private val dataObserver = mockkObserver<List<RecyclerViewItem>>()
  private val errorOccurredObserver = mockkObserver<Unit>()

  @Before
  fun startUp() {
    clearAllMocks()
    viewModel = HomeViewModel(
      navigation = navigation,
      getHomeUseCase = getHomeUseCase
    ).apply {
      isLoadingLive.observeForever(isLoadingObserver)
      dataLive.observeForever(dataObserver)
      errorOccurredLiveEvent.observeForever(errorOccurredObserver)
    }
  }

  @Test
  fun `verify flow when data loaded successfully`() = runBlockingTest {
    executeSuccessFetch()
  }

  @Test
  fun `verify flow when data was refreshed after successful fetch`() = runBlockingTest {
    executeSuccessFetch()
    clearAllMocks()
    coEvery { getHomeUseCase() } returns UseCaseResult.Success(DataFixtures.home)

    viewModel.refreshData()

    coVerifySequence {
      // Refresh
      isLoadingObserver.onChanged(false)
      getHomeUseCase()
      isLoadingObserver.onChanged(false)
      dataObserver.onChanged(listOf(DataFixtures.home.toUiModel()))
    }
  }

  @Test
  fun `verify flow when data loading failed`() = runBlockingTest {
    executeFailureFetch()
  }

  @Test
  fun `verify flow when refresh failed after successful fetch`() = runBlockingTest {
    executeSuccessFetch()
    clearAllMocks()

    coEvery { getHomeUseCase() } returns UseCaseResult.Failure(Unit)

    viewModel.refreshData()

    coVerifySequence {
      // Loading should not be shown as we invoke refresh from swipe refresh layout
      isLoadingObserver.onChanged(false)
      getHomeUseCase()
      isLoadingObserver.onChanged(false)
      dataObserver.onChanged(emptyList())
      errorOccurredObserver.onChanged(Unit)
    }
  }

  @Test
  fun `verify flow when refresh succeed after failed fetch`() = runBlockingTest {
    executeFailureFetch()
    clearAllMocks()
    executeSuccessFetch()
  }

  @Test
  fun `verify flow when refresh failed after failed fetch`() = runBlockingTest {
    executeFailureFetch()
    clearAllMocks()
    executeFailureFetch()
  }

  @Test
  fun `view model navigates to profile`() {
    viewModel.navigateToProfile()

    viewModel.navigationLiveEvent.value shouldBeEqualTo navigation.navigateToProfile()
  }

  @Test
  fun `view model navigates to receipts`() {
    viewModel.navigateToReceiptScan()

    viewModel.navigationLiveEvent.value shouldBeEqualTo navigation.navigateToReceiptScan()
  }

  @Test
  fun `view model navigates to machine code enter`() {
    viewModel.navigateToMachineEnter()

    viewModel.navigationLiveEvent.value shouldBeEqualTo navigation.navigateToMachineEnter()
  }

  @Test
  fun `view model navigates to machine scan`() {
    viewModel.navigateToMachineScan()

    viewModel.navigationLiveEvent.value shouldBeEqualTo navigation.navigateToMachineScan()
  }

  private fun executeSuccessFetch() {
    val home = DataFixtures.home
    coEvery { getHomeUseCase() } returns UseCaseResult.Success(home)

    viewModel.refreshData()

    coVerifySequence {
      isLoadingObserver.onChanged(true)
      getHomeUseCase()
      isLoadingObserver.onChanged(false)
      dataObserver.onChanged(listOf(home.toUiModel()))
    }
  }

  private fun executeFailureFetch() {
    coEvery { getHomeUseCase() } returns UseCaseResult.Failure(Unit)

    viewModel.refreshData()

    coVerifySequence {
      isLoadingObserver.onChanged(true)
      getHomeUseCase()
      isLoadingObserver.onChanged(false)
      dataObserver.onChanged(emptyList())
      errorOccurredObserver.onChanged(Unit)
    }
  }

}
