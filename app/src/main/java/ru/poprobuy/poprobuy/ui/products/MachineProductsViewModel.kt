package ru.poprobuy.poprobuy.ui.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.cash.exhaustive.Exhaustive
import com.github.ajalt.timberkt.d
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.poprobuy.poprobuy.core.navigation.AppNavigation
import ru.poprobuy.poprobuy.core.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.core.ui.BaseViewModel
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingCellUiModel
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.extension.asLiveData
import ru.poprobuy.poprobuy.ui.products.select.MachineProductSelectionInteractor
import java.util.*
import kotlin.math.roundToLong

// TODO: 26.11.2020 Tests
class MachineProductsViewModel(
  private val receiptId: Int,
  private val vendingMachine: VendingMachineUiModel,
  private val navigation: MachineProductsNavigation,
  private val productSelectionInteractor: MachineProductSelectionInteractor,
) : BaseViewModel() {

  val dataLive: MutableLiveData<List<RecyclerViewItem>> get() = _dataLive
  private val _dataLive = MutableLiveData<List<RecyclerViewItem>>()

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive = _isLoadingLive.asLiveData()

  private val _timerStateLive = MutableLiveData<TimerState>()
  val timerStateLive = _timerStateLive.asLiveData()

  private val _commandLive = LiveEvent<MachineProductsCommand>()
  val commandLive = _commandLive.asLiveData()

  private var timerEnd = Date(System.currentTimeMillis() + vendingMachine.assignExpiresIn * 1000)

  private var timerJob: Job? = null
  private var commandsCollectorJob: Job? = null

  init {
    viewModelScope.launch {
      productSelectionInteractor.clearState()
      _dataLive.value = vendingMachine.cells
    }
  }

  override fun onStart() {
    super.onStart()
    startTimer()
    startInteractorCommandsCollector()
  }

  override fun onStop() {
    super.onStop()
    cancelJobs()
  }

  override fun onCleared() {
    super.onCleared()
    cancelJobs()
  }

  fun takeProduct(vendingCell: VendingCellUiModel) {
    _commandLive.value = MachineProductsCommand.ShowSelectionDialog(
      receiptId = receiptId,
      vendingMachineId = vendingMachine.id,
      vendingCell = vendingCell
    )
  }

  private fun startInteractorCommandsCollector() {
    viewModelScope.launch {
      productSelectionInteractor.commandEvent.collect { command ->
        @Exhaustive
        when (command) {
          MachineProductSelectionInteractor.Command.NavigateToHome -> {
            navigation.navigateToHome().navigate()
          }
          is MachineProductSelectionInteractor.Command.ShowErrorDialog -> {
            _commandLive.postValue(MachineProductsCommand.ShowErrorDialog(command.error))
          }
        }
      }
    }
  }

  @ObsoleteCoroutinesApi
  private fun startTimer() {
    d { "Starting timer job" }
    timerJob?.cancel()
    timerJob = viewModelScope.launch {
      val ticker = ticker(COUNTDOWN_INTERVAL, COUNTDOWN_START_DELAY)
      for (ignored in ticker) {
        val state = getTimerState(SELECTION_TIME)
        d { "Updating timer state - $state" }
        _timerStateLive.postValue(state)

        if (state.progress >= SELECTION_TIME) {
          cancel()
          AppNavigation.navigateBack().navigate()
        }
      }
    }
  }

  private fun getTimerState(duration: Int): TimerState {
    val currentTime = Date()

    val remainder = timerEnd.time - currentTime.time
    val progress = (duration * 1000 - remainder) / 1000F
    val remainingSeconds = (remainder / 1000F).roundToLong()

    return TimerState(
      progress = progress,
      maxProgress = duration,
      timeRemaining = remainingSeconds
    )
  }

  private fun cancelJobs() {
    d { "Jobs canceled" }
    timerJob?.cancel()
    commandsCollectorJob?.cancel()
  }

  data class TimerState(
    val progress: Float,
    val maxProgress: Int,
    val timeRemaining: Long,
  )

  companion object {
    private const val SELECTION_TIME = 60
    private const val COUNTDOWN_INTERVAL = 1000L
    private const val COUNTDOWN_START_DELAY = 0L
  }

}
