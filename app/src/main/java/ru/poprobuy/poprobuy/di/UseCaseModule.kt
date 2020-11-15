package ru.poprobuy.poprobuy.di

import org.koin.dsl.module
import ru.poprobuy.poprobuy.usecase.ClearUserDataUseCase
import ru.poprobuy.poprobuy.usecase.GetUserAuthStateUseCase
import ru.poprobuy.poprobuy.usecase.auth.AuthenticationUseCase
import ru.poprobuy.poprobuy.usecase.auth.RefreshTokenUseCase
import ru.poprobuy.poprobuy.usecase.auth.RefreshTokenWorkerUseCase
import ru.poprobuy.poprobuy.usecase.auth.RequestConfirmationCodeUseCase
import ru.poprobuy.poprobuy.usecase.home.GetHomeUseCase
import ru.poprobuy.poprobuy.usecase.receipt.CreateReceiptUseCase
import ru.poprobuy.poprobuy.usecase.receipt.GetReceiptsUseCase
import ru.poprobuy.poprobuy.usecase.user.GetUserInfoUseCase
import ru.poprobuy.poprobuy.usecase.user.UpdateUserDetailsUseCase

val useCaseModule = module {
  // Auth
  factory { GetUserAuthStateUseCase(get()) }
  factory { RequestConfirmationCodeUseCase(get()) }
  factory { AuthenticationUseCase(get(), get()) }
  factory { RefreshTokenUseCase(get(), get()) }
  factory { RefreshTokenWorkerUseCase(get(), get(), get()) }

  // Home
  factory { GetHomeUseCase(get()) }

  // User
  factory { UpdateUserDetailsUseCase(get()) }
  factory { GetUserInfoUseCase(get()) }

  // Receipt
  factory { GetReceiptsUseCase(get()) }
  factory { CreateReceiptUseCase(get()) }

  // System
  factory { ClearUserDataUseCase(get()) }
}
