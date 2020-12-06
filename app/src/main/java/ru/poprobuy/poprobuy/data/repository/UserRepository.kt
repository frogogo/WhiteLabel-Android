package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.util.network.NetworkError

interface UserRepository {

  suspend fun updateUser(email: String, name: String): Result<Unit, NetworkError<ErrorResponse>>

  suspend fun fetchUser(): Result<User, NetworkError<ErrorResponse>>

  fun getUser(): User?

  fun saveUser(user: User)

}
