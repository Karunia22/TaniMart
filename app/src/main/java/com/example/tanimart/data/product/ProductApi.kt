
package com.example.tanimart.data.product

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ProductApi {

    @GET("products")
    suspend fun getProducts(): ProductListResponse

    @GET("products/{productId}")
    suspend fun getProduct(
        @Path("productId") productId: Int
    ): ProductDetailResponse


    @GET("seller/products")
    suspend fun getMyProducts(): ProductListResponse

    @Multipart
    @POST("seller/products")
    suspend fun createProduct(
        @Part("nama_produk") name: RequestBody,
        @Part("deskripsi") description: RequestBody,
        @Part("harga") price: RequestBody,
        @Part("stok") stock: RequestBody,
        @Part("berat") weight: RequestBody,
        @Part("status") status: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): CreateProductResponse


    @PATCH("products/{productId}")
    suspend fun updateProduct(
        @Path("productId") productId: Int,
        @Body fields: Map<String, String>
    ): CreateProductResponse
}
