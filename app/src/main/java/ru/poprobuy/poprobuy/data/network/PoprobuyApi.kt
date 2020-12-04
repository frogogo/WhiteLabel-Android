package ru.poprobuy.poprobuy.data.network

import retrofit2.Response
import retrofit2.http.*
import ru.poprobuy.poprobuy.data.model.api.auth.*
import ru.poprobuy.poprobuy.data.model.api.home.HomeResponse
import ru.poprobuy.poprobuy.data.model.api.machine.TakeProductRequest
import ru.poprobuy.poprobuy.data.model.api.machine.VendingMachine
import ru.poprobuy.poprobuy.data.model.api.machine.VendingMachineAssignRequest
import ru.poprobuy.poprobuy.data.model.api.receipt.Receipt
import ru.poprobuy.poprobuy.data.model.api.receipt.ReceiptCreationRequest
import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.data.model.api.user.UserUpdateRequest
import ru.poprobuy.poprobuy.data.network.annotation.ProvideMachineSessionId
import ru.poprobuy.poprobuy.data.network.annotation.TakeMachineSessionId

interface PoprobuyApi {

  @POST("user")
  suspend fun requestPasswordCode(@Body body: ConfirmationCodeRequest): Response<ConfirmationCodeRequestResponse>

  @POST("user_token")
  suspend fun authenticate(@Body body: AuthenticationRequest): Response<AuthenticationResponse>

  @POST("user_token")
  suspend fun refreshToken(@Body body: TokenRefreshRequest): Response<AuthenticationResponse>

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

  @TakeMachineSessionId
  @ProvideMachineSessionId
  @POST("vending_machines/{machineId}/assign")
  suspend fun assignVendingMachine(
    @Path("machineId") machineId: String,
    @Body body: VendingMachineAssignRequest,
  ): Response<VendingMachine>

  @ProvideMachineSessionId
  @POST("vending_machines/{machineId}/take_item")
  suspend fun takeProduct(
    @Path("machineId") machineId: Int,
    @Body body: TakeProductRequest,
  ): Response<Unit>

}
