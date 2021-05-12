package ru.frogogo.whitelabel.ui.onboarding

import androidx.lifecycle.MutableLiveData
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.core.ui.BaseViewModel
import ru.frogogo.whitelabel.data.repository.OnboardingRepository
import ru.frogogo.whitelabel.extension.asLiveData

class OnboardingViewModel(
  private val onboardingRepository: OnboardingRepository,
  private val navigation: OnboardingNavigation,
) : BaseViewModel() {

  private val _pagesLive = MutableLiveData<List<RecyclerViewItem>>()
  val pagesLive = _pagesLive.asLiveData()

  init {
    _pagesLive.postValue(onboardingRepository.getPages())
  }

  fun completeOnboarding() {
    onboardingRepository.setOnboardingCompleted()
    navigation.navigateToAuth().navigate()
  }
}
