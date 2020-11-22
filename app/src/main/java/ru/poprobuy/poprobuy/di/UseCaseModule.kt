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
import ru.poprobuy.poprobuy.usecase.vending_machine.AssignVendingMachineUseCase

val useCaseModule = module {
  // Auth
  single { GetUserAuthStateUseCase(get()) }
  single { RequestConfirmationCodeUseCase(get()) }
  single { AuthenticationUseCase(get(), get()) }
  single { RefreshTokenUseCase(get(), get()) }
  single { RefreshTokenWorkerUseCase(get(), get(), get()) }

  // Home
  single { GetHomeUseCase(get()) }

  // User
  single { UpdateUserDetailsUseCase(get()) }
  single { GetUserInfoUseCase(get()) }

  // Receipt
  single { GetReceiptsUseCase(get()) }
  single { CreateReceiptUseCase(get()) }

  // Vending Machine
  single { AssignVendingMachineUseCase(get()) }

  // System
  single { ClearUserDataUseCase(get()) }
}
