package com.example.tanimart.data.network

import com.example.tanimart.data.address.AddressApi
import com.example.tanimart.data.auth.AccountApi
import com.example.tanimart.data.auth.LoginApi
import com.example.tanimart.data.auth.LogoutApi
import com.example.tanimart.data.auth.RegisterApi
import com.example.tanimart.data.auth.UserApi
import com.example.tanimart.data.cart.CartApi
import com.example.tanimart.data.cart.CartRepository
import com.example.tanimart.data.checkout.CheckoutApi
import com.example.tanimart.data.order.OrderApiService
import com.example.tanimart.data.order.OrderRepository
import com.example.tanimart.data.payment.PaymentApi
import com.example.tanimart.data.product.ProductApi
import com.example.tanimart.data.seller.SalesApi
import com.example.tanimart.data.seller.SellerApplicationApi
import com.example.tanimart.data.seller.ShopApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val BASE_URL: String
        get() = ApiConfig.baseUrl

    private val client: OkHttpClient by lazy {

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(
                AuthInterceptor {
                    TokenStorage.getToken()
                }
            )
            .build()
    }

    private val retrofit: Retrofit by lazy {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val addressApi: AddressApi by lazy {
        retrofit.create(AddressApi::class.java)
    }

    val loginApi: LoginApi by lazy {
        retrofit.create(LoginApi::class.java)
    }

    val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }

    val logoutApi: LogoutApi by lazy {
        retrofit.create(LogoutApi::class.java)
    }

    val registerApi: RegisterApi by lazy {
        retrofit.create(RegisterApi::class.java)
    }

    val productApi: ProductApi by lazy {
        retrofit.create(ProductApi::class.java)
    }

    val cartApi: CartApi by lazy {
        retrofit.create(CartApi::class.java)
    }

    val cartRepository: CartRepository by lazy {
        CartRepository(cartApi)
    }

    val checkoutApi: CheckoutApi by lazy {
        retrofit.create(CheckoutApi::class.java)
    }

    val paymentApi: PaymentApi by lazy {
        retrofit.create(PaymentApi::class.java)
    }

    val orderApi: OrderApiService by lazy {
        retrofit.create(OrderApiService::class.java)
    }

    val orderRepository: OrderRepository by lazy {
        OrderRepository(orderApi)
    }

    val accountApi: AccountApi by lazy {
        retrofit.create(AccountApi::class.java)
    }

    val sellerApplicationApi: SellerApplicationApi by lazy {
        retrofit.create(SellerApplicationApi::class.java)
    }

    val shopApi: ShopApi by lazy {
        retrofit.create(ShopApi::class.java)
    }

    val salesApi: SalesApi by lazy {
        retrofit.create(SalesApi::class.java)
    }
}