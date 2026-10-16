    package com.example.tanimart.data.address

    import retrofit2.http.Body
    import retrofit2.http.DELETE
    import retrofit2.http.GET
    import retrofit2.http.PATCH
    import retrofit2.http.POST
    import retrofit2.http.Path

    interface AddressApi {

        @GET("addresses")
        suspend fun getAddresses(): AddressListResponse

        @POST("addresses")
        suspend fun addAddress(
            @Body request: AddressRequest
        ): AddressSingleResponse

        @PATCH("addresses/{id}")
        suspend fun updateAddress(
            @Path("id") id: Int,
            @Body request: AddressRequest
        ): AddressSingleResponse

        @DELETE("addresses/{id}")
        suspend fun deleteAddress(
            @Path("id") id: Int
        ): MessageResponse

        @PATCH("addresses/{id}/primary")
        suspend fun setPrimary(
            @Path("id") id: Int
        ): AddressSingleResponse
    }