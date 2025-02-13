package ru.frogogo.whitelabel.ui.home.data

import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.ui.home.model.HomeEmptyState
import ru.frogogo.whitelabel.ui.home.model.HomeInstructions
import ru.frogogo.whitelabel.ui.home.model.HomeReceiptsButton
import ru.frogogo.whitelabel.ui.home.model.HomeScanUnavailable
import ru.frogogo.whitelabel.ui.home.model.HomeSectionHeader

class HomeDataFactoryImpl : HomeDataFactory {

  override fun create(state: HomeState): List<RecyclerViewItem> {
    val list = mutableListOf<RecyclerViewItem>()

    when (state) {
      is HomeState.Empty -> buildEmptyState(state, list)
      is HomeState.Progress -> buildContentState(state, list)
    }

    if (state.items.isNotEmpty()) {
      list += HomeSectionHeader(R.string.home_empty_items)
      list += state.items
    }

    return list
  }

  private fun buildEmptyState(
    state: HomeState.Empty,
    list: MutableList<RecyclerViewItem>,
  ) {
    list += HomeEmptyState(state.promotion)
    list += HomeInstructions
  }

  private fun buildContentState(
    state: HomeState.Progress,
    list: MutableList<RecyclerViewItem>,
  ) {
    list += state.progress

    if (state.coupons.isNotEmpty()) {
      list += HomeSectionHeader(R.string.home_section_coupons)
      list += state.coupons
    }

    list += HomeReceiptsButton

    if (!state.scanAvailable) {
      list += HomeScanUnavailable
    }
  }
}
