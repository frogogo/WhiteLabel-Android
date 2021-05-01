package ru.frogogo.whitelabel.ui.home.delegate

import kotlinx.coroutines.launch
import ru.frogogo.whitelabel.core.handle
import ru.frogogo.whitelabel.core.ui.BaseViewModelDelegate
import ru.frogogo.whitelabel.usecase.home.GetHomeUseCase
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider

class HomeDataLoadDelegateImpl(
  dispatchersProvider: DispatchersProvider,
  private val stateHandlerDelegate: HomeStateHandlerDelegate,
  private val getHomeUseCase: GetHomeUseCase,
) : BaseViewModelDelegate(dispatchersProvider),
  HomeDataLoadDelegate {

  override fun refreshData() {
    scope.launch {
      stateHandlerDelegate.showLoader()
      val result = getHomeUseCase()

      result.handle(
        onSuccess = { stateHandlerDelegate.showData(it) },
        onFailure = { stateHandlerDelegate.showError() }
      )
    }
  }
}
