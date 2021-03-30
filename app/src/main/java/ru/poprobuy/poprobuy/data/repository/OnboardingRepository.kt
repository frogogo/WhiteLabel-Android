package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.data.model.ui.onboarding.OnboardingPage

interface OnboardingRepository {

  fun getPages(): List<OnboardingPage>

  fun setOnboardingCompleted()
}
