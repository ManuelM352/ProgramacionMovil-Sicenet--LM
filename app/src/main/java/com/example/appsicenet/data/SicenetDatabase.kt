package com.example.appsicenet.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TableCargaAcademica::class], version = 1, exportSchema = false)
abstract class SicenetDatabasee : RoomDatabase(){
    abstract fun cargaAcademicaDAO(): CargaAcademicaDAO
    companion object {
        @Volatile
        private var Instance: SicenetDatabasee? = null

        fun getDatabasee(context: Context): SicenetDatabasee {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, SicenetDatabasee::class.java, "sicenet-database")
                    .build().also { Instance = it }
            }
        }
    }
}
