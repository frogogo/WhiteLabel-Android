package ru.poprobuy.poprobuy.ui.onboarding

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.data.model.ui.onboarding.OnboardingPage
import ru.poprobuy.poprobuy.databinding.ItemOnboardingPageBinding

object OnboardingAdapterDelegates {

  fun pageDelegate() = adapterDelegateViewBinding<OnboardingPage, RecyclerViewItem, ItemOnboardingPageBinding>(
    viewBinding = { layoutInflater, root -> ItemOnboardingPageBinding.inflate(layoutInflater, root, false) }
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
