package com.example.artisania_mobile_views.network

import com.example.artisania_mobile_views.models.Order
import com.example.artisania_mobile_views.models.Product
import com.example.avanceproyecto_atenisa.comunicacion.ApiResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProductsApiService {
    @GET("/api/products")
    fun getProducts(): Call<ApiResponse>

    @POST("/api/products/create")
    fun addProduct(@Body product: Product): Call<Void>

    @POST("/api/orders/create")
    fun addOrder(@Body Order: Order): Call<Void>
}