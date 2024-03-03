package com.example.appsicenet.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appsicenet.data.database.dao.CalfFinalDao
import com.example.appsicenet.data.database.dao.PerfilDao
import com.example.appsicenet.data.database.dao.loginDao
import com.example.appsicenet.data.database.entities.CargaAcademicaEntities
import com.example.appsicenet.data.database.entities.Credentials
import com.example.appsicenet.data.database.entities.PerfilEntities

@Database(entities = [CargaAcademicaEntities::class, PerfilEntities::class, Credentials::class], version = 1)
abstract class SicenetDatabase: RoomDatabase() {
    abstract fun getCalfFinal(): CalfFinalDao
    abstract fun getPerfilDao(): PerfilDao // Agrega el DAO para la entidad del perfil

    abstract fun getLogin(): loginDao // Agrega el DAO para la entidad del login

    companion object {
        @Volatile
        private var Instance: SicenetDatabase? = null

        fun getDatabase(context: Context): SicenetDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, SicenetDatabase::class.java, "sicenet-database")
                    .build().also { Instance = it }
            }
        }
    }
}
