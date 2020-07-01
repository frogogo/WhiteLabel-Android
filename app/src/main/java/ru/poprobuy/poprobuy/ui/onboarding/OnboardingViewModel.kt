package ru.poprobuy.poprobuy.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.repository.OnboardingRepository
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel

class OnboardingViewModel(
  private val onboardingRepository: OnboardingRepository,
  private val navigation: OnboardingNavigation
) : BaseViewModel() {

  private val _pagesLive = MutableLiveData<List<RecyclerViewItem>>()
  val pagesLive: LiveData<List<RecyclerViewItem>> get() = _pagesLive

  init {
    _pagesLive.postValue(onboardingRepository.getPages())
  }

  fun completeOnboarding() {
    onboardingRepository.setOnboardingCompleted()
    navigation.navigateToAuth().navigate()
  }

}
