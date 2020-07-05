package ru.poprobuy.poprobuy.arch.recycler

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.poprobuy.poprobuy.data.model.ui.EmptyState
import ru.poprobuy.poprobuy.databinding.ItemEmptyListBinding
import ru.poprobuy.poprobuy.extension.setVisible

object BaseAdapterDelegates {

  fun emptyListDelegate() = adapterDelegateViewBinding<EmptyState, RecyclerViewItem, ItemEmptyListBinding>(
    viewBinding = { layoutInflater, root -> ItemEmptyListBinding.inflate(layoutInflater, root, false) }
  ) {

    bind {
      binding.apply {
        // Image
        imageViewIcon.setImageResource(item.imageResId)

        // Title
        when {
          item.titleResId != null -> textViewTitle.setText(item.titleResId!!)
          item.title != null -> textViewTitle.text = item.title!!
          else -> textViewTitle.setVisible(false)
        }

      }
    }
  }

}
