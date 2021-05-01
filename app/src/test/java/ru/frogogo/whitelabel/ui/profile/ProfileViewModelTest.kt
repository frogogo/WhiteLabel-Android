package ru.frogogo.whitelabel.ui.profile

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.frogogo.test.DataFixtures
import ru.frogogo.test.base.ViewModelTestJUnit5
import ru.frogogo.whitelabel.core.Event
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.ui.profile.ProfileUiModel
import ru.frogogo.whitelabel.data.repository.AuthRepository
import ru.frogogo.whitelabel.data.repository.UserRepository
import ru.frogogo.test.mockkObserver
import ru.frogogo.test.testError
import ru.frogogo.whitelabel.usecase.user.GetUserInfoUseCase
import ru.frogogo.whitelabel.util.ProfileUtils
import ru.frogogo.whitelabel.util.network.NetworkError

@ExperimentalCoroutinesApi
class ProfileViewModelTest : ViewModelTestJUnit5() {

  private lateinit var viewModel: ProfileViewModel

  private val getUserInfoUseCase: GetUserInfoUseCase = mockk(relaxed = true)
  private val userRepository: UserRepository = mockk(relaxed = true)
  private val authRepository: AuthRepository = mockk(relaxed = true)
  private val navigation: ProfileNavigation = mockk(relaxed = true)
  private val profileUtils: ProfileUtils = mockk(relaxed = true)

  private val isLoadingObserver = mockkObserver<Boolean>()
  private val profileObserver = mockkObserver<ProfileUiModel>()
  private val errorOccurredObserver = mockkObserver<Unit>()

  @BeforeEach
  fun startUp() {
    viewModel = ProfileViewModel(
      getUserInfoUseCase = getUserInfoUseCase,
      userRepository = userRepository,
      authRepository = authRepository,
      navigation = navigation,
      profileUtils = profileUtils
    ).apply {
      isLoadingLive.observeForever(isLoadingObserver)
      profileLive.observeForever(profileObserver)
      errorOccurredLiveEvent.observeForever(errorOccurredObserver)
    }
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `verify flow when user data cached and network data loaded`() = runBlockingTest {
    every { userRepository.getUser() } returns DataFixtures.user
    coEvery { getUserInfoUseCase() } returns Result.Success(DataFixtures.user)

    viewModel.loadProfile()

    verifySequence {
      // Cached data
      profileObserver.onChanged(isNull(inverse = true))
      // Network data
      profileObserver.onChanged(isNull(inverse = true))
    }
  }

  @Test
  fun `verify flow when user data cached and network request failed`() = runBlockingTest {
    every { userRepository.getUser() } returns DataFixtures.user
    coEvery { getUserInfoUseCase() } returns Result.Failure(NetworkError.testError())

    viewModel.loadProfile()

    coVerifySequence {
      userRepository.getUser()
      profileObserver.onChanged(isNull(inverse = true))
      getUserInfoUseCase()
    }
  }

  @Test
  fun `verify flow when no cached user data and network data is loaded`() = runBlockingTest {
    every { userRepository.getUser() } returns null
    coEvery { getUserInfoUseCase() } returns Result.Success(DataFixtures.user)

    viewModel.loadProfile()

    coVerifySequence {
      userRepository.getUser()
      isLoadingObserver.onChanged(true)
      getUserInfoUseCase()
      profileObserver.onChanged(isNull(inverse = true))
      isLoadingObserver.onChanged(false)
    }
  }

  @Test
  fun `verify flow when no cached user data and network request failed`() = runBlockingTest {
    every { userRepository.getUser() } returns null
    coEvery { getUserInfoUseCase() } returns Result.Failure(NetworkError.testError())

    viewModel.loadProfile()

    coVerifySequence {
      userRepository.getUser()
      isLoadingObserver.onChanged(true)
      getUserInfoUseCase()
      errorOccurredObserver.onChanged(Unit)
    }
  }

  @Test
  fun `view model navigates to receipts`() {
    viewModel.navigateToReceipts()

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToReceipts())
  }
}
