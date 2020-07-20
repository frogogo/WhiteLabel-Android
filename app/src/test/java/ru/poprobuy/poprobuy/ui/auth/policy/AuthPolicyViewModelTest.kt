package ru.poprobuy.poprobuy.ui.auth.policy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.poprobuy.poprobuy.data.repository.AuthRepository

class AuthPolicyViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: AuthPolicyViewModel
  private val authRepository: AuthRepository = mockk(relaxed = true)
  private val navigation: AuthPolicyNavigation = mockk(relaxed = true)

  @Before
  fun startUp() {
    viewModel = AuthPolicyViewModel(authRepository, navigation)
  }

  @Test
  fun `policy accept state is stored on going next`() {
    viewModel.navigateNext()

    verify {
      authRepository.setPolicyAccepted()
      navigation.navigateToAuthPhoneEnter()
    }

    viewModel.navigationLiveEvent.value shouldBeEqualTo navigation.navigateToAuthPhoneEnter()
  }

}
