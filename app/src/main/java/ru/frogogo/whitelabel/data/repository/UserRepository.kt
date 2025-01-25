package ru.frogogo.whitelabel.data.repository

import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.api.user.User
import ru.frogogo.whitelabel.util.network.NetworkError

interface UserRepository {

  suspend fun updateUser(email: String, name: String): Result<Unit, NetworkError<ErrorResponse>>

  suspend fun fetchUser(): Result<User, NetworkError<ErrorResponse>>

  fun getUser(): User?

  fun saveUser(user: User)

  fun getReceivedCouponsCount(): Int

  fun saveReceivedCouponsCount(count: Int)
}
