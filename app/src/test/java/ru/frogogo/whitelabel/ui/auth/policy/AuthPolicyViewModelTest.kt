package ru.frogogo.whitelabel.ui.auth.policy

import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.frogogo.test.base.ViewModelTestJUnit5
import ru.frogogo.whitelabel.core.Event
import ru.frogogo.whitelabel.data.repository.AuthRepository

@ExperimentalCoroutinesApi
class AuthPolicyViewModelTest : ViewModelTestJUnit5() {

  private lateinit var viewModel: AuthPolicyViewModel

  private val authRepository: AuthRepository = mockk(relaxed = true)
  private val navigation: AuthPolicyNavigation = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    viewModel = AuthPolicyViewModel(
      authRepository = authRepository,
      navigation = navigation
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `policy accept state is stored on going next`() {
    viewModel.navigateNext()

    verify {
      authRepository.setPolicyAccepted()
      navigation.navigateToAuthPhoneEnter()
    }
    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToAuthPhoneEnter())
  }
}
