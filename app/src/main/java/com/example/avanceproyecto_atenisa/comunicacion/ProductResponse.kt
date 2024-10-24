package com.example.avanceproyecto_atenisa.comunicacion

import com.example.avanceproyecto_atenisa.models.Product

class ProductResponse(

    private var name: String,
    private var price: Double,
    private var description: String,
    private var image: String
){

    fun toProduct(): Product {
        return Product(
            name = name,
            price = price,
            description = description,
            image = image,

        )
    }
}