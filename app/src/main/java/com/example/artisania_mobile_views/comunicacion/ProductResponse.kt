package com.example.avanceproyecto_atenisa.comunicacion

import com.example.artisania_mobile_views.models.Product
import java.io.Serializable


class ProductResponse(

    private var id: String,
    private var nombre: String,
    private var descripcion: String,
    private var precio: Double,
    private var detalles: String,
    private var detallesDelArtesano: String,
    private var parametrosPersonalizacion: ParametrosPersonalizacion,
    private var tamaño: Int,
    private var inputText: String,
    private var gravado: String,
    private var categoria: String,
    private var imagen: String,
    private var imagenesDetalle: List<ImagenDetalle>,
    private var autor: String,
    private var caracteristicas: List<Caracteristica>
) {
    fun toProduct(): Product {
        return Product(
            id = null, // Assuming id is auto-generated
            nombre = nombre,
            precio = precio,
            descripcion = descripcion,
            detalles = detalles,
            detallesDelArtesano = detallesDelArtesano,
            parametrosPersonalizacion = parametrosPersonalizacion?.gravado, // Add null check here
            tamaño = tamaño,
            inputText = inputText,
            gravado = gravado,
            categoria = categoria,
            imagen = imagen,
            imagenesDetalle = imagenesDetalle.joinToString { it.imagenUrl },
            autor = autor,
            caracteristicas = caracteristicas
        )
    }
}

data class ParametrosPersonalizacion(
    var parametros: List<Parametro>,
    var gravado: String
): Serializable

data class Parametro(
    var nombre: String,
    var valores: List<Valor>
)

data class Valor(
    var valor: String
)

data class ImagenDetalle(
    var imagenUrl: String
)

data class Caracteristica(
    var nombre: String,
    var valor: String
): Serializable