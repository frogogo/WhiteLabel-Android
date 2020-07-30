package ru.poprobuy.poprobuy.di

import org.koin.dsl.module
import ru.poprobuy.poprobuy.usecase.GetUserAuthStateUseCase
import ru.poprobuy.poprobuy.usecase.auth.AuthenticationUseCase
import ru.poprobuy.poprobuy.usecase.auth.RequestConfirmationCodeUseCase
import ru.poprobuy.poprobuy.usecase.receipt.CreateReceiptUseCase
import ru.poprobuy.poprobuy.usecase.receipt.GetReceiptsUseCase
import ru.poprobuy.poprobuy.usecase.user.GetUserInfoUseCase
import ru.poprobuy.poprobuy.usecase.user.UpdateUserDetailsUseCase

val useCaseModule = module {
  // Auth
  factory { GetUserAuthStateUseCase(get()) }
  factory { RequestConfirmationCodeUseCase(get()) }
  factory { AuthenticationUseCase(get()) }

  // User
  factory { UpdateUserDetailsUseCase(get()) }
  factory { GetUserInfoUseCase(get()) }

  // Receipt
  factory { GetReceiptsUseCase(get()) }
  factory { CreateReceiptUseCase(get()) }
}
