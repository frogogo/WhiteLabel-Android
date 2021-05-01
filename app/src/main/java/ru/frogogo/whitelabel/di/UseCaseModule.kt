package ru.frogogo.whitelabel.di

import org.koin.dsl.module
import ru.frogogo.whitelabel.usecase.ClearUserDataUseCase
import ru.frogogo.whitelabel.usecase.GetUserAuthStateUseCase
import ru.frogogo.whitelabel.usecase.auth.AuthenticationUseCase
import ru.frogogo.whitelabel.usecase.auth.RefreshTokenUseCase
import ru.frogogo.whitelabel.usecase.auth.RefreshTokenWorkerUseCase
import ru.frogogo.whitelabel.usecase.auth.RequestConfirmationCodeUseCase
import ru.frogogo.whitelabel.usecase.home.GetHomeUseCase
import ru.frogogo.whitelabel.usecase.receipt.CreateReceiptUseCase
import ru.frogogo.whitelabel.usecase.receipt.GetReceiptsUseCase
import ru.frogogo.whitelabel.usecase.user.GetUserInfoUseCase
import ru.frogogo.whitelabel.usecase.user.UpdateUserDetailsUseCase
import ru.frogogo.whitelabel.usecase.vending_machine.AssignVendingMachineUseCase
import ru.frogogo.whitelabel.usecase.vending_machine.TakeProductUseCase

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
  single { TakeProductUseCase(get()) }

  // System
  single { ClearUserDataUseCase(get()) }
}
