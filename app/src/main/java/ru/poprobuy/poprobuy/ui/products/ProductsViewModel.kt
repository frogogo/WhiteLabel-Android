package ru.poprobuy.poprobuy.ui.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ticker
import ru.poprobuy.poprobuy.arch.navigation.AppNavigation
import ru.poprobuy.poprobuy.arch.recycler.RecyclerViewItem
import ru.poprobuy.poprobuy.arch.ui.BaseViewModel
import ru.poprobuy.poprobuy.data.model.ui.product.ProductUiModel
import ru.poprobuy.poprobuy.extension.asLiveData
import java.util.*
import kotlin.math.roundToLong
import kotlin.random.Random

class ProductsViewModel : BaseViewModel() {

  val dataLive: MutableLiveData<List<RecyclerViewItem>> get() = _dataLive
  private val _dataLive = MutableLiveData<List<RecyclerViewItem>>()

  private val _isLoadingLive = MutableLiveData<Boolean>()
  val isLoadingLive = _isLoadingLive.asLiveData()

  private val _timerStateLive = MutableLiveData<TimerState>()
  val timerStateLive = _timerStateLive.asLiveData()

  private var timerJob: Job? = null
  private var timerEnd = Date(System.currentTimeMillis() + SELECTION_TIME * 1000)

  init {
    viewModelScope.launch {
      _isLoadingLive.postValue(true)
      delay(750)
      val list = arrayListOf<ProductUiModel>()
      list += ProductUiModel(
        id = 1,
        name = "Чай черный Greenfield",
        imageUrl = "https://cloud.egin.al/s/Df2qMJwnHcTkqtP/preview",
        inStock = true,
        triedBefore = false
      )
      list += ProductUiModel(
        id = 2,
        name = "Батончик Kinder Bueno",
        imageUrl = "https://cloud.egin.al/s/FonHWFxK7njHkR7/preview",
        inStock = false,
        triedBefore = false
      )
      list += ProductUiModel(
        id = 3,
        name = "Печенье Полет Овсяное",
        imageUrl = "https://cloud.egin.al/s/tTcR7YyzacrDqii/preview",
        inStock = true,
        triedBefore = true
      )
      list += ProductUiModel(
        id = 4,
        name = "Биойогурт питьевой Активиа",
        imageUrl = "https://cloud.egin.al/s/YdFZBqZim55ndMt/preview",
        inStock = false,
        triedBefore = true
      )

      list += List(40) {
        ProductUiModel(
          id = 5 + it,
          name = "item $it",
          imageUrl = "https://picsum.photos/${300 + it}",
          inStock = Random.nextBoolean(),
          triedBefore = Random.nextBoolean()
        )
      }

      _dataLive.postValue(list)
      _isLoadingLive.postValue(false)
    }
  }

  override fun onStart() {
    super.onStart()
    d { "Starting timer job" }
    timerJob?.cancel()
    timerJob = viewModelScope.launch(Dispatchers.IO) {
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

  override fun onStop() {
    super.onStop()
    timerJob?.cancel()
    d { "Timer job canceled" }
  }

  override fun onCleared() {
    super.onCleared()
    timerJob?.cancel()
    d { "Timer job canceled" }
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

  data class TimerState(
    val progress: Float,
    val maxProgress: Int,
    val timeRemaining: Long
  )

  companion object {
    private const val SELECTION_TIME = 60
    private const val COUNTDOWN_INTERVAL = 1000L
    private const val COUNTDOWN_START_DELAY = 0L
  }

}
