package ru.frogogo.whitelabel.ui.onboarding

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.onboarding.OnboardingPage
import ru.frogogo.whitelabel.databinding.ItemOnboardingPageBinding

object OnboardingAdapterDelegates {

  fun pageDelegate() = adapterDelegateViewBinding<OnboardingPage, RecyclerViewItem, ItemOnboardingPageBinding>(
    viewBinding = { layoutInflater, root -> ItemOnboardingPageBinding.inflate(layoutInflater, root, false) },
  ) {
    bind {
      binding.apply {
        imageViewIcon.setImageResource(item.iconResId)
        textViewTitle.setText(item.titleResId)
        textViewDescription.setText(item.descriptionResId)
      }
    }
  }
}
