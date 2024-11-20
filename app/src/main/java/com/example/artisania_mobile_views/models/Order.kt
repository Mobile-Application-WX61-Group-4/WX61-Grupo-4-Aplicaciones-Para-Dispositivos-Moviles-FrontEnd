package com.example.artisania_mobile_views.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Order(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "productID")
    var productId: String = " ",

    @ColumnInfo(name = "product")
    var product: String,

    @ColumnInfo(name = "Parametros")
    val parameters: List<Parameter>? = null,

    @ColumnInfo(name = "price")
    var price: Double,
    )
class Parameter(
    var name: String,
    var value: String
)

{
}