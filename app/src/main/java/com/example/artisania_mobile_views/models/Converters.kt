package com.example.artisania_mobile_views.models

import androidx.room.TypeConverter
import com.example.avanceproyecto_atenisa.comunicacion.Caracteristica
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromCaracteristicaList(value: List<Caracteristica>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toCaracteristicaList(value: String): List<Caracteristica>? {
        val type = object : TypeToken<List<Caracteristica>>() {}.type
        return Gson().fromJson(value, type)
    }
}