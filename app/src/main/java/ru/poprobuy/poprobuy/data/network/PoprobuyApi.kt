package ru.poprobuy.poprobuy.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationRequest
import ru.poprobuy.poprobuy.data.model.api.auth.AuthenticationResponse
import ru.poprobuy.poprobuy.data.model.api.auth.ConfirmationCodeRequest
import ru.poprobuy.poprobuy.data.model.api.auth.ConfirmationCodeRequestResponse
import ru.poprobuy.poprobuy.data.model.api.home.HomeResponse
import ru.poprobuy.poprobuy.data.model.api.receipt.Receipt
import ru.poprobuy.poprobuy.data.model.api.receipt.ReceiptCreationRequest
import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.data.model.api.user.UserUpdateRequest

interface PoprobuyApi {

  @POST("user")
  suspend fun requestPasswordCode(@Body body: ConfirmationCodeRequest): Response<ConfirmationCodeRequestResponse>

  @POST("user_token")
  suspend fun authenticate(@Body body: AuthenticationRequest): Response<AuthenticationResponse>

  @PATCH("user")
  suspend fun updateUser(@Body body: UserUpdateRequest): Response<Unit>

  @GET("user")
  suspend fun getUser(): Response<User>

  @GET("receipts")
  suspend fun getReceipts(): Response<List<Receipt>>

  /**
   * Request for activating qr string from receipt
   */
  @POST("receipts")
  suspend fun createReceipt(@Body body: ReceiptCreationRequest): Response<Unit>

  @GET("home")
  suspend fun getHome(): Response<HomeResponse>

}
