package com.example.appsicenet.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appsicenet.data.database.dao.CalfFinalDao
import com.example.appsicenet.data.database.dao.CalfUnidadDao
import com.example.appsicenet.data.database.dao.CargaAcademicaDao
import com.example.appsicenet.data.database.dao.PerfilDao
import com.example.appsicenet.data.database.dao.loginDao
import com.example.appsicenet.data.database.entities.CalfUnidadEnties
import com.example.appsicenet.data.database.entities.CargaAcademicaEntities
import com.example.appsicenet.data.database.entities.Credentials
import com.example.appsicenet.data.database.entities.PerfilEntities
import com.example.appsicenet.data.database.entities.cargaAcademicaEnties

@Database(entities = [CargaAcademicaEntities::class, PerfilEntities::class, Credentials::class, CalfUnidadEnties::class, cargaAcademicaEnties::class], version = 1)
abstract class SicenetDatabase: RoomDatabase() {
    abstract fun getCalfFinal(): CalfFinalDao
    abstract fun getPerfilDao(): PerfilDao

    abstract fun getLogin(): loginDao
    abstract fun getCalfUnidad(): CalfUnidadDao
    abstract fun getCargaAcademica(): CargaAcademicaDao
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
