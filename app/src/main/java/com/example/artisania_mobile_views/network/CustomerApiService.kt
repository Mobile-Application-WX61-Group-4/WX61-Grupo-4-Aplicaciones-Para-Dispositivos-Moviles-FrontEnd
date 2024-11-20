package com.example.artisania_mobile_views.network

import com.example.artisania_mobile_views.models.Customer
import com.example.artisania_mobile_views.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class LoginRequest(val correo: String, val contraseña: String)
data class LoginResponse(val token: String?)

interface CustomerApiService {
    @POST("api/customers/register")
    fun registerUser(@Body user: User): Call<Void>

    @POST("api/customers/login")
    fun loginUser(@Body request: LoginRequest): Call<String>

    @GET("api/customers")
    fun getCustomers(): Call<List<Customer>>
}