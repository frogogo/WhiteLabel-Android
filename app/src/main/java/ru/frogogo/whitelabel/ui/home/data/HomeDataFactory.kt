package ru.frogogo.whitelabel.ui.home.data

import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.data.model.ui.home.HomeState

interface HomeDataFactory {

  fun create(state: HomeState): List<RecyclerViewItem>
}
