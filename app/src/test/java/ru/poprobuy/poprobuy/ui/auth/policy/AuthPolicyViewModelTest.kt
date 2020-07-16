package ru.poprobuy.poprobuy.ui.auth.policy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.poprobuy.poprobuy.data.repository.AuthRepository
import kotlin.test.assertEquals

class AuthPolicyViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private lateinit var policyViewModel: AuthPolicyViewModel
  private val authRepository: AuthRepository = mockk(relaxed = true)
  private val policyNavigation: AuthPolicyNavigation = mockk(relaxed = true)

  @Before
  fun startUp() {
    policyViewModel = AuthPolicyViewModel(authRepository, policyNavigation)
  }

  @Test
  fun `policy accept state is stored on going next`() {
    policyViewModel.navigateNext()

    verify {
      authRepository.setPolicyAccepted()
      policyNavigation.navigateToAuthPhoneEnter()
    }

    assertEquals(policyNavigation.navigateToAuthPhoneEnter(), policyViewModel.navigationLiveEvent.value)
  }

}
