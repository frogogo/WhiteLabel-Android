package ru.poprobuy.poprobuy.ui.onboarding

import androidx.lifecycle.MutableLiveData
import ru.poprobuy.poprobuy.core.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.core.ui.BaseViewModel
import ru.poprobuy.poprobuy.data.repository.OnboardingRepository
import ru.poprobuy.poprobuy.extension.asLiveData

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
