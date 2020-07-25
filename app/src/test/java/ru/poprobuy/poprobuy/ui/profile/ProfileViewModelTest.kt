package ru.poprobuy.poprobuy.ui.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.poprobuy.poprobuy.CoroutinesTestRule
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.mockkObserver
import ru.poprobuy.poprobuy.ui.profile.ProfileViewModel.ViewState
import ru.poprobuy.poprobuy.usecase.UseCaseResult
import ru.poprobuy.poprobuy.usecase.user.GetUserInfoUseCase
import ru.poprobuy.poprobuy.util.ProfileUtils

@ExperimentalCoroutinesApi
class ProfileViewModelTest {

  @get:Rule
  val coroutineTestRule = CoroutinesTestRule()

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: ProfileViewModel
  private val getUserInfoUseCase: GetUserInfoUseCase = mockk(relaxed = true)
  private val userRepository: UserRepository = mockk(relaxed = true)
  private val authRepository: AuthRepository = mockk(relaxed = true)
  private val navigation: ProfileNavigation = mockk(relaxed = true)
  private val profileUtils: ProfileUtils = mockk(relaxed = true)

  @Before
  fun startUp() {
    clearAllMocks()
    viewModel = ProfileViewModel(
      getUserInfoUseCase = getUserInfoUseCase,
      userRepository = userRepository,
      authRepository = authRepository,
      navigation = navigation,
      profileUtils = profileUtils
    )
  }

  @Test
  fun `verify flow when user data cached and network data loaded`() = runBlockingTest {
    val stateObserver = mockkObserver<ViewState>()
    viewModel.stateLiveData.observeForever(stateObserver)
    every { userRepository.getUser() } returns DataFixtures.user
    coEvery { getUserInfoUseCase() } returns UseCaseResult.Success(DataFixtures.user)

    viewModel.loadProfile()

    verifySequence {
      // Cached data
      stateObserver.onChanged(matchViewState(profileIsNull = false))
      // Network data
      stateObserver.onChanged(matchViewState(profileIsNull = false))
    }

    viewModel.stateLiveData.value?.apply {
      isLoading.shouldBeFalse()
      isError.shouldBeFalse()
      profile.shouldNotBeNull()
    }
  }

  @Test
  fun `verify flow when user data cached and network request failed`() = runBlockingTest {
    val stateObserver = mockkObserver<ViewState>()
    viewModel.stateLiveData.observeForever(stateObserver)
    every { userRepository.getUser() } returns DataFixtures.user
    coEvery { getUserInfoUseCase() } returns UseCaseResult.Failure()

    viewModel.loadProfile()

    verifySequence {
      // Cached data
      stateObserver.onChanged(matchViewState(profileIsNull = false))
      // Error not passed as data exists
      stateObserver.onChanged(matchViewState(profileIsNull = false))
    }

    viewModel.stateLiveData.value?.apply {
      isLoading.shouldBeFalse()
      isError.shouldBeFalse()
      profile.shouldNotBeNull()
    }
  }

  @Test
  fun `verify flow when no cached user data and network data is loaded`() = runBlockingTest {
    val stateObserver = mockkObserver<ViewState>()
    viewModel.stateLiveData.observeForever(stateObserver)
    every { userRepository.getUser() } returns null
    coEvery { getUserInfoUseCase() } returns UseCaseResult.Success(DataFixtures.user)

    viewModel.loadProfile()

    verifySequence {
      stateObserver.onChanged(matchViewState(isLoading = true))
      stateObserver.onChanged(matchViewState(profileIsNull = false))
    }

    viewModel.stateLiveData.value?.apply {
      isLoading.shouldBeFalse()
      isError.shouldBeFalse()
      profile.shouldNotBeNull()
    }
  }

  @Test
  fun `verify flow when no cached user data and network request failed`() = runBlockingTest {
    val stateObserver = mockkObserver<ViewState>()
    viewModel.stateLiveData.observeForever(stateObserver)
    every { userRepository.getUser() } returns null
    coEvery { getUserInfoUseCase() } returns UseCaseResult.Failure()

    viewModel.loadProfile()

    verifySequence {
      stateObserver.onChanged(matchViewState(isLoading = true))
      stateObserver.onChanged(matchViewState(isError = true))
    }

    viewModel.stateLiveData.value?.apply {
      isLoading.shouldBeFalse()
      isError.shouldBeTrue()
      profile.shouldBeNull()
    }
  }

  @Test
  fun `view model navigates to receipts`() {
    viewModel.navigateToReceipts()

    viewModel.navigationLiveEvent.value shouldBeEqualTo navigation.navigateToReceipts()
  }

  /**
   * Matcher that defines default view state and allow changes
   */
  private fun MockKMatcherScope.matchViewState(
    isLoading: Boolean = ViewState().isLoading,
    isError: Boolean = ViewState().isError,
    profileIsNull: Boolean = true
  ) = match<ViewState> {
    it.isError == isError &&
        it.isLoading == isLoading &&
        (it.profile == null) == profileIsNull
  }

}
