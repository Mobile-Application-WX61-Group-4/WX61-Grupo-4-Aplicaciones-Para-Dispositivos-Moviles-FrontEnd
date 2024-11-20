package com.example.artisania_mobile_views.models

import android.os.Parcel
import android.os.Parcelable
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
    var caracteristicas: List<Caracteristica>? = null,

    var cantidad: Int = 0
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readValue(Int::class.java.classLoader) as? Int,
        nombre = parcel.readString() ?: "",
        precio = parcel.readDouble(),
        descripcion = parcel.readString() ?: "",
        detalles = parcel.readString(),
        detallesDelArtesano = parcel.readString(),
        parametrosPersonalizacion = parcel.readString(),
        tamaño = parcel.readValue(Int::class.java.classLoader) as? Int,
        inputText = parcel.readString(),
        gravado = parcel.readString(),
        categoria = parcel.readString(),
        imagen = parcel.readString() ?: "",
        imagenesDetalle = parcel.readString(),
        autor = parcel.readString(),
        cantidad = parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(nombre)
        parcel.writeDouble(precio)
        parcel.writeString(descripcion)
        parcel.writeString(detalles)
        parcel.writeString(detallesDelArtesano)
        parcel.writeString(parametrosPersonalizacion)
        parcel.writeValue(tamaño)
        parcel.writeString(inputText)
        parcel.writeString(gravado)
        parcel.writeString(categoria)
        parcel.writeString(imagen)
        parcel.writeString(imagenesDetalle)
        parcel.writeString(autor)
        parcel.writeInt(cantidad)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}