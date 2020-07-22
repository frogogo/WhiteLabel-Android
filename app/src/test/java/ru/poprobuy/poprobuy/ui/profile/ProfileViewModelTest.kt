package ru.poprobuy.poprobuy.ui.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.poprobuy.poprobuy.CoroutinesTestRule
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.model.ui.profile.ProfileUiModel
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.mockkObserver
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
  fun `user data cached, network data not changed, loader not shown`() = runBlockingTest {
    val isLoadingObserver = mockkObserver<Boolean>()
    val profileObserver = mockkObserver<ProfileUiModel>()
    viewModel.apply {
      isLoadingLive.observeForever(isLoadingObserver)
      profileLive.observeForever(profileObserver)
    }
    every { userRepository.getUser() } returns DataFixtures.user
    coEvery { getUserInfoUseCase() } returns UseCaseResult.Success(DataFixtures.user)

    viewModel.onCreate()

    verifySequence {
      profileObserver.onChanged(isNull(inverse = true))
    }
  }

  /**
   * The difference is that network data is not equals to local stored and live data should be updated twice
   */
  @Test
  fun `user data cached, network data changed, loader not shown`() = runBlockingTest {
    val isLoadingObserver = mockkObserver<Boolean>()
    val profileObserver = mockkObserver<ProfileUiModel>()
    viewModel.apply {
      isLoadingLive.observeForever(isLoadingObserver)
      profileLive.observeForever(profileObserver)
    }
    every { userRepository.getUser() } returns DataFixtures.user
    coEvery { getUserInfoUseCase() } returns UseCaseResult.Success(DataFixtures.user.copy(email = ""))

    viewModel.onCreate()

    verifySequence {
      profileObserver.onChanged(isNull(inverse = true))
      profileObserver.onChanged(isNull(inverse = true))
    }
  }

  @Test
  fun `no cached data, loader should be shown`() = runBlockingTest {
    val isLoadingObserver = mockkObserver<Boolean>()
    val profileObserver = mockkObserver<ProfileUiModel>()
    viewModel.apply {
      isLoadingLive.observeForever(isLoadingObserver)
      profileLive.observeForever(profileObserver)
    }
    every { userRepository.getUser() } returns null
    coEvery { getUserInfoUseCase() } returns UseCaseResult.Success(DataFixtures.user)

    viewModel.onCreate()

    verifySequence {
      isLoadingObserver.onChanged(true)
      profileObserver.onChanged(isNull(inverse = true))
      isLoadingObserver.onChanged(false)
    }
  }

  @Test
  fun `view model navigates to receipts`() {
    viewModel.navigateToReceipts()

    viewModel.navigationLiveEvent.value shouldBeEqualTo navigation.navigateToReceipts()
  }

}
