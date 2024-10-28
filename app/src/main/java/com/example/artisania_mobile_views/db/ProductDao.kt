package com.example.avanceproyecto_atenisa.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.artisania_mobile_views.models.Product


@Dao
interface ProductDao {

    @Insert
    fun insertOne(product: Product)

    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Delete
    fun delete(news: Product)

}