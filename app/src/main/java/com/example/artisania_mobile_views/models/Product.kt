package com.example.artisania_mobile_views.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Product(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "price")
    var price: Double,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "image")
    var image: String

): Serializable