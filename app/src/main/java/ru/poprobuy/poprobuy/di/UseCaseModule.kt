package ru.poprobuy.poprobuy.di

import org.koin.dsl.module
import ru.poprobuy.poprobuy.usecase.GetUserAuthStateUseCase

val useCaseModule = module {
  // Auth
  factory { GetUserAuthStateUseCase(get()) }
}
