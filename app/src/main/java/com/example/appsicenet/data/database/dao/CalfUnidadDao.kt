package com.example.appsicenet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsicenet.data.database.entities.CalfUnidadEnties

@Dao
interface CalfUnidadDao {
    @Query("SELECT * FROM calificacionUnidad_table ORDER BY materia DESC")
    suspend fun getAllCalfUnidad(): List<CalfUnidadEnties>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCalfUnidad(calfUnidad:List<CalfUnidadEnties>)

    @Query("DELETE FROM calificacionUnidad_table")
    suspend fun deleteAllFromCalfUnidad()
}