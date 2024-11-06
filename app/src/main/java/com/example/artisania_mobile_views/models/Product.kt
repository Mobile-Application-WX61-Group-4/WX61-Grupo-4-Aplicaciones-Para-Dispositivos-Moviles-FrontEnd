package com.example.artisania_mobile_views.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.avanceproyecto_atenisa.comunicacion.Caracteristica
import java.io.Serializable

@Entity
class Product(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "nombre")
    var nombre: String,

    @ColumnInfo(name = "precio")
    var precio: Double,

    @ColumnInfo(name = "descripcion")
    var descripcion: String,

    @ColumnInfo(name = "detalles")
    var detalles: String? = null,

    @ColumnInfo(name = "detallesDelArtesano")
    var detallesDelArtesano: String? = null,

    @ColumnInfo(name = "parametrosPersonalizacion")
    var parametrosPersonalizacion: String? = null,

    @ColumnInfo(name = "tamaño")
    var tamaño: Int? = null,

    @ColumnInfo(name = "inputText")
    var inputText: String? = null,

    @ColumnInfo(name = "gravado")
    var gravado: String? = null,

    @ColumnInfo(name = "categoria")
    var categoria: String? = null,

    @ColumnInfo(name = "imagen")
    var imagen: String,

    @ColumnInfo(name = "imagenesDetalle")
    var imagenesDetalle: String? = null,

    @ColumnInfo(name = "autor")
    var autor: String? = null,

    @ColumnInfo(name = "caracteristicas")
    var caracteristicas: List<Caracteristica>? = null
) : Serializable