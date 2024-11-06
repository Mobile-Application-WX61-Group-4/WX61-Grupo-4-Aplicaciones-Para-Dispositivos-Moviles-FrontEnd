package com.example.artisania_mobile_views.network

import com.example.artisania_mobile_views.models.Product
import com.example.avanceproyecto_atenisa.comunicacion.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductsApiService {
    @GET("/api/products")
    fun getProducts(): Call<ApiResponse>

    @POST("/api/products/create")
    fun addProduct(@Body product: Product): Call<Void>
}