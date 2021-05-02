package ru.frogogo.whitelabel.ui.home.data

import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.ui.home.model.HomeSectionHeader

class HomeDataFactoryImpl : HomeDataFactory {

  override fun create(state: HomeState): List<RecyclerViewItem> {
    val list = mutableListOf<RecyclerViewItem>()

    when (state) {
      HomeState.Empty -> buildEmptyState(state, list)
      is HomeState.Progress -> buildContentState(state, list)
    }

    return list
  }

  private fun buildEmptyState(
    state: HomeState,
    list: MutableList<RecyclerViewItem>,
  ) {
    // TODO: 02.05.2021  
  }

  private fun buildContentState(
    state: HomeState.Progress,
    list: MutableList<RecyclerViewItem>,
  ) {
    list += state.couponProgress

    if (state.coupons.isNotEmpty()) {
      list += HomeSectionHeader(R.string.home_section_coupons)
      list += state.coupons
    }

    if (state.receipts.isNotEmpty()) {
      list += HomeSectionHeader(R.string.home_section_receipts)
      list += state.receipts
    }
  }
}
