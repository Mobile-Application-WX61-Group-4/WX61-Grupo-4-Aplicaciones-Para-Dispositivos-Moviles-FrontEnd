package com.example.avanceproyecto_atenisa.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.artisania_mobile_views.models.Converters
import com.example.artisania_mobile_views.models.Product

@Database(entities = [Product::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getDao(): ProductDao

    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context, AppDataBase::class.java, "products.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }

}