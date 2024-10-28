package com.example.artisania_mobile_views.network

import com.example.avanceproyecto_atenisa.comunicacion.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProductsApiService {
    @GET("Joaqquin0/prueba_jason/db")
    fun getProducts(): Call<ApiResponse>
}