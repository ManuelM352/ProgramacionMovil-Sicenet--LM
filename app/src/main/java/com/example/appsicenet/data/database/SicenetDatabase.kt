package com.example.appsicenet.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appsicenet.data.database.dao.SicenetDao
import com.example.appsicenet.data.database.entities.SicenetEntities

@Database(entities = [SicenetEntities::class], version = 1)
abstract class SicenetDatabase: RoomDatabase() {
    abstract fun getCalfFinal():SicenetDao

    companion object {
        @Volatile
        private var Instance: SicenetDatabase? = null

        fun getDatabase(context: Context): SicenetDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, SicenetDatabase::class.java, "sicenet-database")
                    .build().also { Instance = it }
            }
        }
    }
}