package ru.frogogo.whitelabel.util.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
  val main: CoroutineDispatcher
  val default: CoroutineDispatcher
  val io: CoroutineDispatcher
  val unconfined: CoroutineDispatcher
}
