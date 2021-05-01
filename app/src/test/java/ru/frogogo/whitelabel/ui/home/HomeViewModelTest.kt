package ru.frogogo.whitelabel.ui.home

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.mapper.toDomain
import ru.frogogo.whitelabel.usecase.home.GetHomeUseCase
import ru.frogogo.whitelabel.core.Event
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.util.network.NetworkError
import ru.frogogo.test.DataFixtures
import ru.frogogo.test.base.ViewModelTestJUnit5
import ru.frogogo.test.mockkObserver
import ru.frogogo.test.testError

@ExperimentalCoroutinesApi
class HomeViewModelTest : ViewModelTestJUnit5() {

  private lateinit var viewModel: HomeViewModel

  private val navigation: HomeNavigation = mockk(relaxed = true)
  private val getHomeUseCase: GetHomeUseCase = mockk(relaxed = true)

  private val isLoadingObserver = mockkObserver<Boolean>()
  private val dataObserver = mockkObserver<List<RecyclerViewItem>>()
  private val errorOccurredObserver = mockkObserver<Unit>()

  @BeforeEach
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

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `verify flow when data loaded successfully`() = runBlockingTest {
    executeSuccessFetch()
  }

  @Test
  fun `verify flow when data was refreshed after successful fetch`() = runBlockingTest {
    executeSuccessFetch()
    clearAllMocks()
    coEvery { getHomeUseCase() } returns Result.Success(DataFixtures.home.toDomain())

    viewModel.refreshData()

    coVerifySequence {
      // Refresh
      isLoadingObserver.onChanged(false)
      getHomeUseCase()
      isLoadingObserver.onChanged(false)
      dataObserver.onChanged(listOf(DataFixtures.home.toDomain()))
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

    coEvery { getHomeUseCase() } returns Result.Failure(NetworkError.testError())

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

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToProfile())
  }

  @Test
  fun `view model navigates to receipts`() {
    viewModel.navigateToReceiptScan()

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToReceiptScan())
  }

  @Test
  fun `view model navigates to machine code enter`() {
    viewModel.navigateToMachineEnter(1)

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToMachineEnter(1))
  }

  @Test
  fun `view model navigates to machine scan`() {
    viewModel.navigateToMachineScan(1)

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToMachineScan(1))
  }

  private fun executeSuccessFetch() {
    val home = DataFixtures.home
    coEvery { getHomeUseCase() } returns Result.Success(home.toDomain())

    viewModel.refreshData()

    coVerifySequence {
      isLoadingObserver.onChanged(true)
      getHomeUseCase()
      isLoadingObserver.onChanged(false)
      dataObserver.onChanged(listOf(home.toDomain()))
    }
  }

  private fun executeFailureFetch() {
    coEvery { getHomeUseCase() } returns Result.Failure(NetworkError.testError())

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
